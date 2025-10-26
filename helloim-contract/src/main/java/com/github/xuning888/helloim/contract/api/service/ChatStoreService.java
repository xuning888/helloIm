package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.entity.ImChat;

/**
 * @author xuning
 * @date 2025/10/7 15:58
 */
public interface ChatStoreService {

    int createOrUpdate(ImChat imChat, String traceId);
}
