package com.github.xuning888.helloim.dispatch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuning
 * @date 2025/8/2 19:14
 */
@EnableDubbo
@SpringBootApplication
public class DispatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchApplication.class, args);
    }
}