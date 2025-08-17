package com.github.xuning888.helloim.gateway.core.handler.impl;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.Conn;
import com.github.xuning888.helloim.gateway.core.handler.DownMsgHandler;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/10 14:34
 */
public class HeadMsgHandler implements DownMsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeadMsgHandler.class);


    @Override
    public void handleDownEvent(DefaultMsgPipeline.PipelineContext ctx, DownCmdEvent event) {
        Conn conn = event.getConn();
        Frame frame = event.getFrame();
        Header header = frame.getHeader();
        int cmdId = header.getCmdId(), seq = header.getSeq();
        try {
            if (conn.isOk()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("发送下行消息, cmdId: {}, seq: {}, traceId: {}", cmdId, seq, event.getTraceId());
                }
                conn.write(frame, event.getTraceId());
                if (logger.isDebugEnabled()) {
                    logger.debug("发送下行消息成功, cmdId:{}, seq: {}, traceId:{}", cmdId, seq, event.getTraceId());
                }
            } else {
                logger.error("Write message, peer is closed, traceId: {}", event.getTraceId());
            }
        } catch (Exception ex) {
            logger.error("Write message to peer failed, traceId:  {}", event.getTraceId(), ex);
        }
        ctx.sendDown(event);
    }
}