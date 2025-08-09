package com.github.xuning888.helloim.gateway.core.conn;


import com.github.xuning888.helloim.contract.frame.Frame;
import org.jboss.netty.channel.Channel;

/**
 * 业务层长连接抽象, 用于多协议支持的抽象
 * @author xuning
 * @date 2025/8/10 1:22
 */
public interface Conn {

    /**
     * 长连接的ID
     * @return id
     */
    String id();

    /**
     * 获取连接关联的channel
     * @return channel
     */
    Channel channel();

    /**
     * 连接是否正常
     * @return ok
     */
    boolean isOk();

    /**
     * 写出数据到peer
     * @param frame 数据帧
     * @param traceId traceId
     */
    void write(Frame frame, String traceId);
}
