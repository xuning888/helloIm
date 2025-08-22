package com.github.xuning888.helloim.message.config;

import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuning
 * @date 2025/8/23 0:41
 */
@Configuration
public class MessageConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "im.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }
}