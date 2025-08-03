package com.github.xuning888.helloim.contract.api.service.gate;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;

/**
 * @author xuning
 * @date 2025/8/2 16:10
 */
public interface DownMsgService {

    void pushMessage(DownMessageReq req);
}
