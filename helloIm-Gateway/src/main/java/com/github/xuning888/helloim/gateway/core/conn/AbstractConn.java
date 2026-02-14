package com.github.xuning888.helloim.gateway.core.conn;


import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import org.jboss.netty.channel.Channel;

/**
 * @author xuning
 * @date 2025/12/6 22:42
 */
public abstract class AbstractConn implements Conn {

    private final Channel channel;

    private final MsgPipeline msgPipeline;

    private final String id;

    public AbstractConn(Channel channel, MsgPipeline msgPipeline) {
        this.channel = channel;
        this.msgPipeline = msgPipeline;
        this.id = String.valueOf(channel.getId());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Channel channel() {
        return this.channel;
    }

    @Override
    public MsgPipeline getMsgPipeline() {
        return this.msgPipeline;
    }
}
