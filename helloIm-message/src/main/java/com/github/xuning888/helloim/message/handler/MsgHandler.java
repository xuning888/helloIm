package com.github.xuning888.helloim.message.handler;

import com.github.xuning888.helloim.contract.dto.MsgContext;

/**
 * @author xuning
 * @date 2025/8/22 23:39
 */
public interface MsgHandler {

    int getCmdId();

    void handleMessage(MsgContext msgContext, String kTraceId);
}
