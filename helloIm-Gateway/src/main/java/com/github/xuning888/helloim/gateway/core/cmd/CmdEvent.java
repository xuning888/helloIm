package com.github.xuning888.helloim.gateway.core.cmd;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.gateway.core.conn.event.ConnEvent;

/**
 * 所有发生在长连接中的指令事件
 * @author xuning
 * @date 2025/8/10 10:51
 */
public interface CmdEvent extends ConnEvent {

    /**
     * 获取数据帧
     * @return frame
     */
    Frame getFrame();
}
