package com.github.xuning888.helloim.gateway.core.handler;

import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.pipeline.DefaultMsgPipeline;

/**
 * @author xuning
 * @date 2025/8/10 11:42
 */
public interface UpMsgHandler extends MsgHandler {

    /**
     * 上行消息处理器
     */
    void handleUpEvent(DefaultMsgPipeline.PipelineContext ctx, UpCmdEvent event);
}
