package com.github.xuning888.helloim.gateway.core.pipeline;

import com.github.xuning888.helloim.gateway.core.cmd.DownCmdEvent;
import com.github.xuning888.helloim.gateway.core.cmd.UpCmdEvent;
import com.github.xuning888.helloim.gateway.core.handler.MsgHandler;

/**
 * @author xuning
 * @date 2025/8/10 11:15
 */
public interface MsgPipeline {

    /**
     * 添加事件处理器
     * @param name name
     * @param msgHandler handler
     */
    void addLast(String name, MsgHandler msgHandler);


    /**
     * 投递上行事件
     * @param upCmdEvent cmd
     */
    void sendUp(UpCmdEvent upCmdEvent);


    /**
     * 投递下行事件
     * @param downCmdEvent cmd
     */
    void sendDown(DownCmdEvent downCmdEvent);
}
