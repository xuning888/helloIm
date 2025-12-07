package com.github.xuning888.helloim.gateway.core.conn;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * @author xuning
 * @date 2025/8/10 23:29
 */
public class TcpConn extends AbstractConn {

    private final Channel channel;

    public TcpConn(Channel channel, MsgPipeline msgPipeline) {
        super(channel, msgPipeline);
        this.channel = channel;
    }

    @Override
    public boolean isOk() {
        return channel.isConnected() && channel.isWritable();
    }

    @Override
    public void close() {
        ChannelFuture closeFuture = channel.close();
        closeFuture.syncUninterruptibly();
    }

    @Override
    public void write(Frame frame, String traceId) {
        byte[] byteArray = frame.toByteArray();
        ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(byteArray);
        ChannelFuture future = channel.write(channelBuffer);
        future.syncUninterruptibly();
    }
}