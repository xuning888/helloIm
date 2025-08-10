package com.github.xuning888.helloim.gateway.core.conn;

import com.github.xuning888.helloim.contract.frame.Frame;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * @author xuning
 * @date 2025/8/10 23:29
 */
public class TcpConn implements Conn {

    private final Channel channel;

    private final String id;

    public TcpConn(Channel channel) {
        this.channel = channel;
        this.id = String.valueOf(channel.getId());
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
}