package com.github.xuning888.helloim.gateway.core.conn;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * @author xuning
 * @date 2025/8/10 23:29
 */
public class TcpConn implements Conn {

    private final Channel channel;

    private final String id;

    private final MsgPipeline msgPipeline;

    public TcpConn(Channel channel, MsgPipeline msgPipeline) {
        this.channel = channel;
        this.id = String.valueOf(channel.getId());
        this.msgPipeline = msgPipeline;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public boolean isOk() {
        return channel.isConnected();
    }

    @Override
    public void close() {
        ChannelFuture closeFuture = channel.close();
        closeFuture.syncUninterruptibly();
    }

    @Override
    public void write(Frame frame, String traceId) {
        byte[] byteArray = frame.toByteArray();
        channel.write(byteArray);
    }

    @Override
    public MsgPipeline getMsgPipeline() {
        return msgPipeline;
    }
}