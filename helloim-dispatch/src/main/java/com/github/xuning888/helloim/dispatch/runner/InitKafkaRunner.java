package com.github.xuning888.helloim.dispatch.runner;

import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import com.github.xuning888.helloim.contract.kafka.MsgKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 初始化KafkaProducer
 * @author xuning
 * @date 2025/8/3 0:24
 */
@Component
public class InitKafkaRunner implements CommandLineRunner {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Override
    public void run(String... args) throws Exception {
        Properties properties = kafkaProperties.buildProducerProperties();
        MsgKafkaProducer.getInstance().init(properties);
    }
}