package com.github.xuning888.helloim.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuning
 * @date 2025/8/3 22:11
 */
@Configuration
public class GateConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "im.gate")
    public GateServerProperties gateServerProperties() {
        return new GateServerProperties();
    }

    @Bean
    public GateAddr gateAddr(GateServerProperties gateServerProperties) {
        return new GateAddr(gateServerProperties.getPort());
    }
}