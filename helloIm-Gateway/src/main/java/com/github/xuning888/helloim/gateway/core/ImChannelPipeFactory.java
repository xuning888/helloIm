package com.github.xuning888.helloim.gateway.core;

import com.github.xuning888.helloim.gateway.config.GateServerProperties;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/8/10 1:07
 */
public class ImChannelPipeFactory implements ChannelPipelineFactory {

    private final GateServerProperties gateServerProperties;
    public final ImChannelUpStreamHandler imChannelUpStreamHandler;

    public ImChannelPipeFactory(GateServerProperties gateServerProperties,
                                ImChannelUpStreamHandler imChannelUpStreamHandler) {
        this.gateServerProperties = gateServerProperties;
        this.imChannelUpStreamHandler = imChannelUpStreamHandler;
    }


    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = new DefaultChannelPipeline();
        // 解码器
        channelPipeline.addLast("decoder",
                new LengthFieldBasedFrameDecoder(gateServerProperties.getMaxFrameSize(), 16, 4));
        // 添加业务处理器
        channelPipeline.addLast("imBiz", imChannelUpStreamHandler);
        return channelPipeline;
    }
}