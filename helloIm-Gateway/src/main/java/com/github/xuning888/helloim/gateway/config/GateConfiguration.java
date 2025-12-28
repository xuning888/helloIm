package com.github.xuning888.helloim.gateway.config;

import com.github.xuning888.helloim.contract.protobuf.MsgCmd;
import com.github.xuning888.helloim.gateway.adapter.UpMsgServiceAdapter;
import com.github.xuning888.helloim.gateway.core.ImChannelPipeFactory;
import com.github.xuning888.helloim.gateway.core.ImChannelUpStreamHandler;
import com.github.xuning888.helloim.gateway.core.TcpChannelUpStreamHandler;
import com.github.xuning888.helloim.gateway.core.cmd.handler.*;
import com.github.xuning888.helloim.gateway.core.handler.impl.HeadMsgHandler;
import com.github.xuning888.helloim.gateway.core.handler.impl.PrometheusHandler;
import com.github.xuning888.helloim.gateway.core.handler.impl.TailMsgHandler;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.core.processor.MessageProcessor;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import com.github.xuning888.helloim.gateway.core.session.DefaultSessionManager;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Value;
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
    public GateAddr gateAddr(@Value("${dubbo.protocol.port}") Integer port,
                             @Value("${im.gate.protocol}") String protocol) {
        return new GateAddr(port, protocol);
    }

    @Bean
    public SessionManager sessionManager(UpMsgServiceAdapter upMsgServiceAdapter, GateAddr addr) {
        return new DefaultSessionManager(upMsgServiceAdapter, addr);
    }

    @Bean
    public HandlerProxy handlerProxy(UpMsgServiceAdapter upMsgServiceAdapter, SessionManager sessionManager, GateAddr gateAddr) {
        DefaultHandler defaultHandler = new DefaultHandler(upMsgServiceAdapter, sessionManager, gateAddr);
        HandlerProxy handlerProxy = new HandlerProxy(defaultHandler);
        // 注册Echo指令的处理器
        handlerProxy.register(MsgCmd.CmdId.CMD_ID_ECHO_VALUE, new EchoHandler());
        // 注册Auth指令
        handlerProxy.register(MsgCmd.CmdId.CMD_ID_AUTH_VALUE, new AuthHandler(upMsgServiceAdapter, sessionManager, gateAddr));
        // 心跳处理
        handlerProxy.register(MsgCmd.CmdId.CMD_ID_HEARTBEAT_VALUE, new HeartbeatHandler(sessionManager));
        return handlerProxy;
    }

    @Bean
    public MsgPipeline msgPipeline(HandlerProxy handlerProxy, PrometheusMeterRegistry registry) {
        DefaultMsgPipeline defaultMsgPipeline = new DefaultMsgPipeline();
        defaultMsgPipeline.addLast("head", new HeadMsgHandler());
        defaultMsgPipeline.addLast("metrics", new PrometheusHandler(registry));
        defaultMsgPipeline.addLast("tail", new TailMsgHandler(handlerProxy));
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
        String protocol = gateServerProperties.getProtocol();
        if ("tcp".equals(protocol)) {
            return new TcpChannelUpStreamHandler(processor);
        } else {
            throw new IllegalArgumentException("Unknown protocol: " + gateServerProperties.getProtocol());
        }
    }

    @Bean
    public ImChannelPipeFactory imChannelPipeFactory(
            GateServerProperties gateServerProperties,
            ImChannelUpStreamHandler imChannelUpStreamHandler) {
        return new ImChannelPipeFactory(gateServerProperties, imChannelUpStreamHandler);
    }
}