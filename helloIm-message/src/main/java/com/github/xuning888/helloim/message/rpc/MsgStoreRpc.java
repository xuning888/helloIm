package com.github.xuning888.helloim.message.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.store.v1.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2026/2/7 14:36
 */
@Service
public class MsgStoreRpc {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreRpc.class);

    @DubboReference(
            methods = {
                    @Method(name = "saveMessage", timeout = 2000, retries = 0)
            }
    )
    private MsgStoreService msgStoreService;

    public boolean saveMessage(ChatMessage chatMessage, String traceId) {
        if (chatMessage == null) {
            logger.warn("saveMessage chatMessage is null, traceId: {}", traceId);
            return false;
        }
        String msgIdStr = chatMessage.getMsgIdStr();
        logger.info("saveMessage msgId: {}, traceId: {}", msgIdStr, traceId);
        try {
            SaveMessageRequest saveMessageRequest = SaveMessageRequest.newBuilder()
                    .setTraceId(traceId)
                    .setChatMessage(chatMessage).build();
            SaveMessageResponse response = this.msgStoreService.saveMessage(saveMessageRequest);
            int raw = response.getResult();
            logger.info("saveMessage raw: {}, msgId: {}, traceId: {}", raw, msgIdStr, traceId);
            return raw > 0;
        } catch (Exception ex) {
            logger.error("saveMessage error, msgId: {}, traceId: {}, errMsg: {}", msgIdStr, traceId, ex.getMessage(), ex);
        }
        return false;
    }

    public List<ChatMessage> getRecentMessages(String userId, String chatId, Integer chatType, Integer limit, String traceId) {
        logger.info("getRecentMessages, userId: {}, chatId: {}, chatType: {}, limit: {}, traceId: {}",
                userId, chatId, chatType, limit, traceId);
        try {
            GetRecentMessagesRequest request = GetRecentMessagesRequest.newBuilder()
                    .setUserId(userId).setChatId(chatId).setChatType(chatType).setLimit(limit).setTraceId(traceId).build();
            GetRecentMessagesResponse response = msgStoreService.getRecentMessages(request);
            List<ChatMessage> recentMessages = response.getMessagesList();
            logger.info("getRecentMessages, recentMessages.size: {}, traceId: {}", recentMessages.size(), traceId);
            return recentMessages;
        } catch (Exception ex) {
            logger.error("getRecentMessages failed, traceId: {}", traceId, ex);
            return Collections.emptyList();
        }
    }

    public List<ChatMessage> selectMessageByServerSeqs(String userId, String chatId, Integer chatType, Set<Long> serverSeqs, String traceId) {
        logger.info("selectMessageByServerSeqs, userId: {}, chatId: {}, chatType: {}, serverSeqs: {}, traceId: {}",
                userId, chatId, chatType, serverSeqs, traceId);
        try {
            SelectMessageByServerSeqsRequest request = SelectMessageByServerSeqsRequest.newBuilder()
                    .setUserId(userId).setChatId(chatId).setChatType(chatType)
                    .addAllServerSeqs(serverSeqs).setTraceId(traceId).build();
            SelectMessageByServerSeqsResponse response = msgStoreService.selectMessageByServerSeqs(request);
            List<ChatMessage> messages = response.getMessagesList();
            logger.info("selectMessageByServerSeqs, messages.size: {}, traceId: {}", response.getMessagesCount(), traceId);
            return messages;
        } catch (Exception ex) {
            logger.error("selectMessageByServerSeqs failed, traceId: {}", traceId, ex);
            return Collections.emptyList();
        }
    }
}
