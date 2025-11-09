package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.dto.ChatMessageDto;

/**
 * @author xuning
 * @date 2025/9/20 23:53
 */
public interface MsgStoreService {

    /**
     * 存储消息
     */
    int saveMessage(ChatMessageDto chatMessageDto, String traceId);

    Long maxServerSeq(String from, String to, Integer chatType, String traceId);

    ChatMessageDto lastMessage(String userId, String chatId, Integer chatType, String traceId);
}
