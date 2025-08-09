package com.github.xuning888.helloim.gateway.runner;

import com.github.xuning888.helloim.gateway.server.GateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/3 22:19
 */
@Component
public class GateInitRunner implements CommandLineRunner {

    @Autowired
    private GateServer gateServer;

    @Override
    public void run(String... args) throws Exception {
        gateServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            gateServer.stop();
        }));
    }
}