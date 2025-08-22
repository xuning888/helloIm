package com.github.xuning888.helloim.message.consumer;

import com.github.xuning888.helloim.contract.kafka.MsgKafkaConsumer;
import com.github.xuning888.helloim.message.handler.MsgDispatchService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.Properties;

/**
 * @author xuning
 * @date 2025/8/22 23:38
 */
public class MessageConsumer extends MsgKafkaConsumer {

    private final MsgDispatchService msgDispatchService;

    public MessageConsumer(MsgDispatchService msgDispatchService,
                           Properties properties, List<String> topics) {
        super(properties, topics);
        this.msgDispatchService = msgDispatchService;
    }

    @Override
    public void processRecord(ConsumerRecord<String, byte[]> record, String traceId) {
        msgDispatchService.dispatchMsg(record, traceId);
    }
}