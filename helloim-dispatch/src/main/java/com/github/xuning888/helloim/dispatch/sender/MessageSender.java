package com.github.xuning888.helloim.dispatch.sender;

import com.github.xuning888.helloim.contract.dto.MsgContext;

/**
 * @author xuning
 * @date 2025/8/2 22:03
 */
public interface MessageSender {

    int cmdId();

    void sendMessage(MsgContext msgContext);
}
