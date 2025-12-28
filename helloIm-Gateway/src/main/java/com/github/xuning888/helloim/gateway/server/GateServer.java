package com.github.xuning888.helloim.gateway.server;

import com.github.xuning888.helloim.gateway.config.GateServerProperties;
import com.github.xuning888.helloim.gateway.core.ImChannelPipeFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author xuning
 * @date 2025/8/3 21:56
 */
@Component
public class GateServer {
    private static final Logger logger = LoggerFactory.getLogger(GateServer.class);

    private Channel channel;
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final GateServerProperties gateServerProperties;
    private final ImChannelPipeFactory imChannelPipeFactory;

    public GateServer(GateServerProperties gateServerProperties, ImChannelPipeFactory imChannelPipeFactory) {
        this.gateServerProperties= gateServerProperties;
        this.imChannelPipeFactory = imChannelPipeFactory;
    }


    public void start() throws Exception {
        logger.info("GateServer starting...");
        try {
            bootstrap.setFactory(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
            bootstrap.setPipelineFactory(imChannelPipeFactory);
            // socket配置
            // 1. 禁用Nagle算法,确保数据包及时发送
            // 2. 接收缓冲区 SO_RCVBUF, 设置64KB
            // 3. 发送缓冲区 SO_SNDBUF, 设置64KB
            // socket = 3KB + 128KB = 131KB
            // 按照现在的配置方式, 单机10w个socket占用的内存 = 131 * 100000 / 1024 / 1024 = 12.5G 且这只是内核所需的内存, JVM的还没算。
            // TODO 目前限制的最大的帧=8192byte = 8KB, 这里根据需要根据实际的压测再调整
            bootstrap.setOption("child.tcpNoDelay", true);
            bootstrap.setOption("child.receiveBufferSize", 65536);
            bootstrap.setOption("child.sendBufferSize", 65536);

            InetSocketAddress addr = new InetSocketAddress(gateServerProperties.getPort());
            channel = bootstrap.bind(addr);
            logger.info("GateServer binding addr: {}", addr);
        } catch (Exception ex) {
            logger.error("GateServer start fail", ex);
            throw new Exception();
        }
        logger.info("GateServer ready to accept connections");
    }

    public void stop() {
        logger.info("GateServer stop begin");
        if (channel != null) {
            channel.close().syncUninterruptibly();
        }
        bootstrap.releaseExternalResources();
        logger.info("GateServer stop success");
    }
}