package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.dto.ChatMessage;

/**
 * @author xuning
 * @date 2025/9/20 23:53
 */
public interface MsgStoreService {

    /**
     * 存储消息
     */
    int saveMessage(ChatMessage chatMessage, String traceId);
}
