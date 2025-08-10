package com.github.xuning888.helloim.gateway.core.cmd;

/**
 * 上行指令处理器, 处理本地指令或者透传指令到路由层
 * @author xuning
 * @date 2025/8/10 13:27
 */
public interface CmdHandler {

    /**
     * cmdEvent
     */
    void handle(CmdEvent cmdEvent) throws Exception;
}
