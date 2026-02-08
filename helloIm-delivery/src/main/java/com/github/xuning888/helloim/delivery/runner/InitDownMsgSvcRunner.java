package com.github.xuning888.helloim.delivery.runner;

import com.github.xuning888.helloim.contract.gateway.ChannelFactoryManager;
import com.github.xuning888.helloim.contract.gateway.DownMsgSvcProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xuning
 * @date 2026/2/8 15:26
 */
@Component
public class InitDownMsgSvcRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitDownMsgSvcRunner.class);

    @Resource
    private DownMsgSvcProperties downMsgSvcProperties;

    @Override
    public void run(String... args) throws Exception {
        logger.info("init config: {}", downMsgSvcProperties);
        ChannelFactoryManager.init(downMsgSvcProperties);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ChannelFactoryManager.shutdown();
            } catch (Exception ex) {
                logger.error("ChannelFactoryManager.shutdown errorMsg: {}", ex.getMessage(), ex);
            }
        }));
    }
}
