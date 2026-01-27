package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.api.request.DownMessageReq;
import com.github.xuning888.helloim.contract.api.response.DownMessageResp;

/**
 * @author xuning
 * @date 2025/8/2 16:10
 */
public interface DownMsgService {

    DownMessageResp pushMessage(DownMessageReq req);
}
