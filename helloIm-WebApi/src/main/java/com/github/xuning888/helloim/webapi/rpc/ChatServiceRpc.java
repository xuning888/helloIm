package com.github.xuning888.helloim.webapi.rpc;


import com.github.xuning888.helloim.api.protobuf.chat.v1.ChatService;
import com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest;
import com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest;
import com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse;
import com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest;
import com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 19:19
 */
@Service
public class ChatServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceRpc.class);

    @DubboReference
    private ChatService chatService;

    public List<ImChat> getALlChat(Long userId, String traceId) {
        GetAllChatRequest request = GetAllChatRequest.newBuilder().setUserId(String.valueOf(userId)).setTraceId(traceId).build();
        try {
            GetAllChatResponse response = chatService.getAllChat(request);
            return response.getChatsList();
        } catch (Exception ex) {
            logger.error("getALlChat, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return Collections.emptyList();
        }
    }

    public ImChat getChat(Long userId, Long chatId, String traceId) {
        GetChatRequest request = GetChatRequest.newBuilder().setUserId(userId).setChatId(chatId).setTraceId(traceId).build();
        try {
            GetChatResponse response = chatService.getChat(request);
            return response.getImChat();
        } catch (Exception ex) {
            logger.error("getChat, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return null;
        }
    }

    public ChatMessage lastMessage(String userId, String chatId, Integer chatType, String traceId) {
        LastMessageRequest request = LastMessageRequest.newBuilder().setUserId(userId).setChatId(chatId).setChatType(chatType).setTraceId(traceId).build();
        try {
            LastMessageResponse response = chatService.lastMessage(request);
            return response.getLastMessage();
        } catch (Exception ex) {
            logger.error("lastMessage, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return null;
        }
    }
}
