package com.github.xuning888.helloim.gateway.core.handler.impl;

import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.handler.HandlerProxy;
import com.github.xuning888.helloim.gateway.core.handler.UpMsgHandler;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuning
 * @date 2025/8/10 13:17
 */
public class TailMsgHandler implements UpMsgHandler {

    private static final Logger logger = LoggerFactory.getLogger(TailMsgHandler.class);

    private final HandlerProxy handlerProxy;

    public TailMsgHandler(HandlerProxy handlerProxy) {
        this.handlerProxy = handlerProxy;
    }

    @Override
    public void handleUpEvent(DefaultMsgPipeline.PipelineContext ctx, UpCmdEvent event) {
        try {
            handlerProxy.handle(event);
        } catch (Exception ex) {
            logger.error("TailMsgHandler, 上行事件处理失败, traceId: {}", event.getTraceId(), ex);
        }
        ctx.sendUp(event);
    }
}