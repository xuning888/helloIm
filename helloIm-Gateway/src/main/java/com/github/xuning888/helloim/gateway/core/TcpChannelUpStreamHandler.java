package com.github.xuning888.helloim.gateway.core;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.conn.TcpConn;
import com.github.xuning888.helloim.gateway.core.processor.Processor;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author xuning
 * @date 2025/8/10 23:28
 */
public class TcpChannelUpStreamHandler extends ImChannelUpStreamHandler {

    private static final Logger logger = LoggerFactory.getLogger(TcpChannelUpStreamHandler.class);
    private final Processor processor;

    public TcpChannelUpStreamHandler(Processor processor) {
        super(processor);
        this.processor = processor;
    }

    @Override
    protected Conn createOrGetConn(Channel channel) {
        TcpConn conn = (TcpConn) channel.getAttachment();
        if (conn != null) {
            return conn;
        }
        conn = new TcpConn(channel, processor.msgPipeline());
        channel.setAttachment(conn);
        return conn;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String traceId = UUID.randomUUID().toString();
        Conn conn = createOrGetConn(e.getChannel());
        if (logger.isDebugEnabled()) {
            logger.debug("received message, conn: {}, traceId: {}", conn.getId(), traceId);
        }
        Object message = e.getMessage();
        Frame frame = null;
        if (message instanceof ByteBuffer) {
            ByteBuffer buffer = (ByteBuffer) message;
            frame = createFrame(conn, buffer);
        } else if (message instanceof BigEndianHeapChannelBuffer) {
            BigEndianHeapChannelBuffer buffer = (BigEndianHeapChannelBuffer) message;
            ByteBuffer byteBuffer = buffer.toByteBuffer();
            frame = createFrame(conn, byteBuffer);
        } else {
            logger.error("received message, message can't convert to ByteBuffer, conn: {}, traceId: {}", conn.getId(), traceId);
            return;
        }
        if (frame == null) {
            logger.error("received message, message can't convert to Frame, conn: {}, traceId: {}", conn.getId(), traceId);
            return;
        }

        if (logger.isDebugEnabled()) {
            Header header = frame.getHeader();
            logger.info("received message, conn: {}, cmdId:{}, seq:{}, traceId: {}",
                    conn.getId(), header.getCmdId(), header.getSeq(), traceId);
        }
        // 尝试ACK消息
        if (conn.ack(frame)) {
            return;
        }
        // 创建上行事件
        UpCmdEvent upCmdEvent = new UpCmdEvent(frame, conn, traceId);
        // 投递上行事件
        processor.handleConnEvent(upCmdEvent);
    }
}