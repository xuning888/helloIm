package com.github.xuning888.helloim.contract.api.service.chatlist;

import com.github.xuning888.helloim.contract.contant.ChatType;

/**
 * @author xuning
 * @date 2025/8/2 19:29
 */
public interface ChatService {

    Long ERROR_SERVER_SEQ = -1L;

    /**
     * 生成服务端的消息序号
     */
    Long serverSeq(String from, String to, ChatType chatType, String traceId);
}
