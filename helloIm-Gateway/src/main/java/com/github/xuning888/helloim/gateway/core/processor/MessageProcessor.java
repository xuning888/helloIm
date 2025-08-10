package com.github.xuning888.helloim.gateway.core.processor;


import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnEvent;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnStateEvent;
import com.github.xuning888.helloim.gateway.core.pipeline.MsgPipeline;
import com.github.xuning888.helloim.gateway.core.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author xuning
 * @date 2025/8/10 15:29
 */
public class MessageProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private final SessionManager sessionManager;

    private final MsgPipeline msgPipeline;

    private final ExecutorService executorService;

    public MessageProcessor(SessionManager sessionManager,
                            MsgPipeline msgPipeline, ExecutorService executorService) {
        this.sessionManager = sessionManager;
        this.msgPipeline = msgPipeline;
        this.executorService = executorService;
    }


    @Override
    public void handleConnEvent(ConnEvent connEvent) {
        String traceId = connEvent.getTraceId();
        try {
            if (connEvent instanceof ConnStateEvent) {
                sessionManager.changeState((ConnStateEvent) connEvent);
            } else if (connEvent instanceof UpCmdEvent) {
                sendUp((UpCmdEvent) connEvent);
            } else if (connEvent instanceof DownCmdEvent) {
                sendDown((DownCmdEvent) connEvent);
            } else {
                logger.error("handlerConnEvent unknown conEvent: {}, traceId: {}", connEvent, traceId);
            }
        } catch (Exception ex) {
            logger.error("handleConnEvent failed traceId: {}", traceId, ex);
        }
    }

    @Override
    public MsgPipeline msgPipeline() {
        return this.msgPipeline;
    }

    private void sendUp(UpCmdEvent upCmdEvent) {
        String traceId = upCmdEvent.getTraceId();
        executorService.execute(() -> {
            try {
                msgPipeline.sendUp(upCmdEvent);
            } catch (Exception ex) {
                logger.error("sendUp error, traceId: {}", traceId);
            }
        });
    }

    private void sendDown(DownCmdEvent downCmdEvent) {
        String traceId = downCmdEvent.getTraceId();
        executorService.execute(() -> {
            try {
                msgPipeline.sendDown(downCmdEvent);
            } catch (Exception ex) {
                logger.error("sendDown error, traceId: {}", traceId);
            }
        });
    }
}