package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import com.github.xuning888.helloim.api.dto.ImChatDto;

import java.util.List;

/**
 * @author xuning
 * @date 2025/8/2 19:29
 */
public interface ChatService {
    /**
     * 生成服务端的消息序号
     */
    Long serverSeq(String from, String to, ChatType chatType, String traceId);

    /**
     * 创建或激活会话
     */
    ImChatDto createOrActivateChat(Long userId, Long chatId, ChatType chatType, String traceId);

    /**
     * 拉去所有的会话
     */
    List<ImChatDto> getALlChat(Long userId, String traceId);

    /**
     * 获取会话最后一条消息
     */
    ChatMessageDto lastMessage(String userId, String chatId, Integer chatType, String traceId);

    /**
     * 获取会话
     */
    ImChatDto getChat(Long userId, Long chatId, String traceId);
}
