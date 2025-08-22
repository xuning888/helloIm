package com.github.xuning888.helloim.delivery.config;

import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuning
 * @date 2025/8/23 1:05
 */
@Configuration
public class DeliveryConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "im.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }
}