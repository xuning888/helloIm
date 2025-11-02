package com.github.xuning888.helloim.message.runner;

import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.github.xuning888.helloim.message.consumer.MessageConsumer;
import com.github.xuning888.helloim.message.handler.MsgDispatchService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author xuning
 * @date 2025/8/23 0:46
 */
@Component
public class KafkaRunner implements CommandLineRunner {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private MsgDispatchService msgDispatchService;

    @Override
    public void run(String... args) throws Exception {

        // 初始化producer
        initKafkaProducer();

        Properties properties = kafkaProperties.buildConsumerProperties();
        MessageConsumer messageConsumer = new MessageConsumer(msgDispatchService, properties, ImmutableList.of(
                Topics.C2C.C2C_SEND_REQ, // 单聊上行
                Topics.C2C.C2C_SEND_RES // 单聊ACK上行
        ));
        messageConsumer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(messageConsumer::stop));
    }


    private void initKafkaProducer() {
        MsgKafkaProducer producer = MsgKafkaProducer.getInstance();
        producer.init(kafkaProperties.buildProducerProperties());
    }
}