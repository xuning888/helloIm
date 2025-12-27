package com.github.xuning888.helloim.delivery.runner;

import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.delivery.consumer.DeliverConsumer;
import com.github.xuning888.helloim.delivery.handler.MsgDispatchService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/23 1:08
 */
@Component
public class DeliveryKafkaRunner implements CommandLineRunner {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private MsgDispatchService msgDispatchService;

    @Override
    public void run(String... args) throws Exception {
        DeliverConsumer deliverConsumer = new DeliverConsumer(
                msgDispatchService,
                kafkaProperties.buildConsumerProperties(),
                ImmutableList.of(
                        Topics.C2C.C2C_PUSH_REQ, // 单聊下行
                        Topics.C2G.C2G_PUSH_REQ // 群聊下行
                )
        );
        deliverConsumer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(deliverConsumer::stop));
    }
}