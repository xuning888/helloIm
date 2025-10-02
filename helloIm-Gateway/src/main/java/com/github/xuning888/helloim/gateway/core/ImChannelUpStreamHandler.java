package com.github.xuning888.helloim.gateway.core;


import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnStateEvent;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import io.netty.handler.codec.TooLongFrameException;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author xuning
 * @date 2025/8/3 23:00
 */
public abstract class ImChannelUpStreamHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImChannelUpStreamHandler.class);

    private final Processor processor;

    public ImChannelUpStreamHandler(Processor processor) {
        this.processor = processor;
    }

    /**
     * 连接建立
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        Channel channel = e.getChannel();
        // 多协议适配，创建Conn
        Conn conn = createOrGetConn(channel);
        String traceId = UUID.randomUUID().toString();
        // 连接建立，出发open实践, 此时并不会创建session
        processor.handleConnEvent(new ConnStateEvent(conn, ConnStateEvent.State.OPEN, traceId));
    }

    /**
     * 连接断开
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        Channel channel = e.getChannel();
        // 多协议适配, 获取长连接
        Conn conn = createOrGetConn(channel);
        String traceId = UUID.randomUUID().toString();
        // 长连接关闭事件下发到业务层
        if (logger.isDebugEnabled()) {
            logger.debug("channelDisconnected traceId: {}", traceId);
        }
        processor.handleConnEvent(new ConnStateEvent(conn, ConnStateEvent.State.CLOSE, traceId));
    }

    /**
     * 接受上行事件
     */
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) e;
            IdleState state = event.getState();
            // 检测到心跳超时, 触发timeout事件
            if (IdleState.ALL_IDLE.equals(state) ||
                    IdleState.READER_IDLE.equals(state) ||
                    IdleState.WRITER_IDLE.equals(state)) {
                String traceId = UUID.randomUUID().toString();
                Conn conn = createOrGetConn(e.getChannel());
                logger.info("channelTimeout traceId: {}", traceId);
                processor.handleConnEvent(new ConnStateEvent(conn, ConnStateEvent.State.TIMEOUT, traceId));
            }
        } else {
            // 其余的上行事件走默认流程
            super.handleUpstream(ctx, e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        String traceId = UUID.randomUUID().toString();
        Throwable cause = e.getCause();
        Channel channel = e.getChannel();
        if (cause instanceof ReadTimeoutException) {
            logger.error("ReadTimeoutException remoteAddr: {}, sessionId: {}, traceId: {}", channel.getRemoteAddress(), channel.getId(), traceId, cause);
        } else if (cause instanceof IOException) {
            logger.error("IOException remoteAddr: {}, sessionId: {}, traceId: {}", channel.getRemoteAddress(), channel.getId(), traceId, cause);
        } else if (cause instanceof TooLongFrameException) {
            logger.error("TooLongFrameException remoteAddr: {}, sessionId: {}, traceId: {}", channel.getRemoteAddress(), channel.getId(), traceId, cause);
        } else {
            logger.error("exceptionCaught, remoteAddr: {}, sessionId: {}, traceId: {}", channel.getRemoteAddress(), channel.getId(), traceId, cause);
        }
        Conn conn = createOrGetConn(channel);
        processor.handleConnEvent(new ConnStateEvent(conn, ConnStateEvent.State.ERROR, traceId));
    }

    protected Frame createFrame(Conn conn, ByteBuffer byteBuffer) {
        Frame frame = null;
        try {
            byte[] bytes = byteBuffer.array();
            frame = Frame.create(bytes);
        } catch (Exception ex) {
            logger.error("createFrame failed sessionId: {}", conn.getId(), ex);
        }
        return frame;
    }

    /**
     * 适配多协议，由子类实现
     * @param channel channel
     * @return Conn
     */
    protected abstract Conn createOrGetConn(Channel channel);
}