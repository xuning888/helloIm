package com.github.xuning888.helloim.gateway.core.handler;

import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;

/**
 * @author xuning
 * @date 2025/8/10 11:42
 */
public interface DownMsgHandler extends MsgHandler {

    /**
     * 处理下行事件的处理器
     * @param ctx ctx
     * @param event 下行事件
     */
    void handleDownEvent(DefaultMsgPipeline.PipelineContext ctx, DownCmdEvent event);
}
