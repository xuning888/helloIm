package com.github.xuning888.helloim.chat.runner;

import com.github.xuning888.helloim.chat.consumer.MessageConsumer;
import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import com.github.xuning888.helloim.contract.kafka.Topics;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/10/8 23:35
 */
@Component
public class KafkaRunner implements CommandLineRunner {

    @Autowired
    private ChatService chatService;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Override
    public void run(String... args) throws Exception {
        MessageConsumer messageConsumer = new MessageConsumer(
                chatService,
                kafkaProperties.buildConsumerProperties(),
                ImmutableList.of(
                        Topics.C2C.C2C_SEND_REQ // 单聊下行
                )
        );
        messageConsumer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(messageConsumer::stop));
    }
}
