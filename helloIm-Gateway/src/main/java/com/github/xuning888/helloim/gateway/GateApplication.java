package com.github.xuning888.helloim.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuning
 * @date 2025/8/2 20:02
 */
@EnableDubbo
@SpringBootApplication
public class GateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateApplication.class, args);
    }
}