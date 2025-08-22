package com.github.xuning888.helloim.delivery.handler;

import com.github.xuning888.helloim.contract.dto.MsgContext;

/**
 * @author xuning
 * @date 2025/8/23 1:07
 */
public interface MsgDeliverHandler {

    int getCmdId();

    void handle(MsgContext msgContext, String kTraceId);
}
