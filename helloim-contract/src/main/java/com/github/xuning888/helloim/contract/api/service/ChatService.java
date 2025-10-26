package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.entity.ImChat;

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
    ImChat createOrActivateChat(Long userId, Long chatId, ChatType chatType, String traceId);

}
