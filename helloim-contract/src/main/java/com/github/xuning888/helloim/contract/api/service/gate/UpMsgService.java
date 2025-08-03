package com.github.xuning888.helloim.contract.api.service.gate;

import com.github.xuning888.helloim.contract.api.request.UpMessageReq;

/**
 * @author xuning
 * @date 2025/8/2 16:08
 */
public interface UpMsgService {

    String sayHello(String name);

    /**
     * 发送上行消息
     * @param req req
     */
    void sendMessage(UpMessageReq req);
}
