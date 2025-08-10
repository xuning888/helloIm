package com.github.xuning888.helloim.gateway.config;

import com.github.xuning888.helloim.gateway.adapter.UpMsgServiceAdapter;
import com.github.xuning888.helloim.gateway.core.ImChannelPipeFactory;
import com.github.xuning888.helloim.gateway.core.ImChannelUpStreamHandler;
import com.github.xuning888.helloim.gateway.core.TcpChannelUpStreamHandler;
import com.github.xuning888.helloim.gateway.core.cmd.handler.DefaultHandler;
import com.github.xuning888.helloim.gateway.core.cmd.handler.HandlerProxy;
import com.github.xuning888.helloim.gateway.core.handler.impl.HeadMsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.impl.TailMsgHandler;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.core.processor.MessageProcessor;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import com.github.xuning888.helloim.gateway.core.session.DefaultSessionManager;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Bean
    public SessionManager sessionManager(UpMsgServiceAdapter upMsgServiceAdapter) {
        return new DefaultSessionManager(upMsgServiceAdapter);
    }

    @Bean
    public MsgPipeline msgPipeline(UpMsgServiceAdapter upMsgServiceAdapter,
                                   SessionManager sessionManager, GateAddr gateAddr) {
        DefaultMsgPipeline defaultMsgPipeline = new DefaultMsgPipeline();
        defaultMsgPipeline.addLast("head", new HeadMsgHandler());
        defaultMsgPipeline.addLast("tail", new TailMsgHandler(new HandlerProxy(new DefaultHandler(upMsgServiceAdapter, sessionManager, gateAddr))));
        return defaultMsgPipeline;
    }

    @Bean
    public Processor processor(SessionManager sessionManager,
                               MsgPipeline msgPipeline,
                               GateServerProperties gateServerProperties) {
        ExecutorService executorService = new ThreadPoolExecutor(
                gateServerProperties.getCoreSize(), gateServerProperties.getMaxPoolSize(),
                5, TimeUnit.MINUTES, new LinkedBlockingDeque<>(gateServerProperties.getQueueSize())
        );
        return new MessageProcessor(sessionManager, msgPipeline, executorService);
    }

    @Bean
    public ImChannelUpStreamHandler imChannelUpStreamHandler(
            GateServerProperties gateServerProperties,
            Processor processor
    ) {
        if (gateServerProperties.getProtocol().equals("tcp")) {
            return new TcpChannelUpStreamHandler(processor);
        }
        throw new IllegalArgumentException("Unknown protocol: " + gateServerProperties.getProtocol());
    }

    @Bean
    public ImChannelPipeFactory imChannelPipeFactory(
            GateServerProperties gateServerProperties,
            ImChannelUpStreamHandler imChannelUpStreamHandler) {
        return new ImChannelPipeFactory(gateServerProperties, imChannelUpStreamHandler);
    }
}