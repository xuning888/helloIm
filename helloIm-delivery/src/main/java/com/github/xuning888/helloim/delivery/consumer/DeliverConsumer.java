package com.github.xuning888.helloim.delivery.consumer;

import com.github.xuning888.helloim.contract.kafka.MsgKafkaConsumer;
import com.github.xuning888.helloim.delivery.handler.MsgDispatchService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.Properties;

/**
 * @author xuning
 * @date 2025/8/23 1:06
 */
public class DeliverConsumer extends MsgKafkaConsumer {

    private final MsgDispatchService msgDispatchService;


    public DeliverConsumer(MsgDispatchService msgDispatchService,
                           Properties properties, List<String> topics) {
        super(properties, topics);
        this.msgDispatchService = msgDispatchService;
    }

    @Override
    public void processRecord(ConsumerRecord<String, byte[]> record, String traceId) {
        msgDispatchService.dispatch(record, traceId);
    }
}