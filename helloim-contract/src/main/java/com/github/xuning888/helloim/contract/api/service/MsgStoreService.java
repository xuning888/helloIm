package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.contant.CommonConstant;
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

    default Long maxServerSeq(String from, String to, Integer chatType, String traceId) {
        return CommonConstant.ERROR_SERVER_SEQ;
    }
}
