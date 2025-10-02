package com.github.xuning888.helloim.contract.kafka;

import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.contract.util.ProtoStuffUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuning
 * @date 2025/8/2 16:02
 */
public class MsgKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(MsgKafkaProducer.class);

    private Producer<String, byte[]> producer  = null;

    private MsgKafkaProducer() {}

    private static class LazyInit {
        private static final MsgKafkaProducer msgKafkaProducer = new MsgKafkaProducer();
    }

    public static MsgKafkaProducer getInstance() {
        return LazyInit.msgKafkaProducer;
    }

    public void init(Properties properties) {
        if (producer == null) {
            producer = new KafkaProducer<>(properties);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (producer != null) {
                    producer.close();
                }
            }));
        }
    }

    public void send(String topic, String key, MsgContext msgContext) {
        String traceId = msgContext.getTraceId();
        send(topic, key, msgContext, new SendCallback(traceId));
    }

    public void send(String topic, String key, MsgContext msgContext, Callback callback) {
        String traceId = msgContext.getTraceId();
        int partition = -1;
        try {
            List<PartitionInfo> partitions = producer.partitionsFor(topic);
            // 如果partition的数量大于1, 那么就尝试根据消息场景路由partition
            if (partitions.size() > 1) {
                partition = partition(msgContext, partitions.size());
            }

            // 使用protoStuff将消息序列化
            byte[] value = ProtoStuffUtils.serialize(msgContext);

            ProducerRecord<String, byte[]> record = null;
            if (partition != -1) {
                record = new ProducerRecord<>(topic, partition, key, value);
            } else {
                record = new ProducerRecord<>(topic, key, value);
            }

            doSend(record, callback);
        } catch (Exception ex) {
            logger.error("Failed to send message traceId: {}", traceId);
        }
    }


    private void doSend(ProducerRecord<String, byte[]> record, Callback callback) {
        this.producer.send(record, callback);
    }


    private int partition(MsgContext msgContext, int partitionSize) {
        int cmdId = msgContext.getFrame().getHeader().getCmdId();
        if (MsgCmd.CmdId.CMD_ID_C2CSEND_VALUE == cmdId ||
                MsgCmd.CmdId.CMD_ID_C2CPUSH_VALUE == cmdId) { // 单聊
            String chat = Stream.of(msgContext.getMsgFrom(), msgContext.getMsgTo())
                    .sorted(String::compareTo)
                    .collect(Collectors.joining("_"));
            return Utils.toPositive(Utils.murmur2(chat.getBytes(StandardCharsets.UTF_8))) % partitionSize;
        } else if (MsgCmd.CmdId.CMD_ID_C2GSEND_VALUE == cmdId ||
                MsgCmd.CmdId.CMD_ID_C2GPUSH_VALUE == cmdId) { // 群聊
            String groupId = msgContext.getMsgTo();
            Long groupIdInt64 = null;
            try {
                groupIdInt64 = Long.parseLong(groupId);
            } catch (Exception ex) {
                logger.error("partition failed to parseLong, groupId: {}", groupId, ex);
                return -1;
            }
            return (int) (groupIdInt64 % partitionSize);
        } else {
            return -1;
        }
    }

    static class SendCallback implements Callback {

        private final String traceId;

        public SendCallback(String traceId) {
            this.traceId = traceId;
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            String topic = recordMetadata.topic();
            int partition = recordMetadata.partition();
            if (e != null) {
                logger.error("SendCallback error, topic: {}, partition: {}, traceId: {}", topic, partition, traceId);
                return;
            }
            logger.info("SendCallback success. topic: {}, partition: {}, traceId: {}", topic, partition, traceId);
        }
    }
}