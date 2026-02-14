package com.github.xuning888.helloim.gateway.core.conn;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
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
    String getId();

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
     * 关闭长连接
     */
    void close();

    /**
     * 写出数据到peer, 并标记下行消息是否需要ACK
     */
    void write(Frame frame, String traceId);

    /**
     * 获取channel关联的pipeline
     */
    MsgPipeline getMsgPipeline();
}
