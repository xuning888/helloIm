package com.github.xuning888.helloim.message.rpc;


import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/12/27 16:27
 */
@Component
public class MsgStoreSvcClient {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreSvcClient.class);

    @DubboReference(
            methods = {
                    @Method(name = "saveMessage", timeout = 2000, retries = 0)
            }
    )
    private MsgStoreService msgStoreService;

    public boolean saveMessage(ChatMessageDto chatMessageDto, String traceId) {
        if (chatMessageDto == null) {
            logger.warn("saveMessage chatMessageDto is null, traceId: {}", traceId);
            return false;
        }
        String msgIdStr = chatMessageDto.getMsgIdStr();
        logger.info("saveMessage msgId: {}, traceId: {}", msgIdStr, traceId);
        try {
            int raw = this.msgStoreService.saveMessage(chatMessageDto, traceId);
            logger.info("saveMessage raw: {}, msgId: {}, traceId: {}", raw, msgIdStr, traceId);
            return raw > 0;
        } catch (Exception ex) {
            logger.error("saveMessage error, msgId: {}, traceId: {}, errMsg: {}", msgIdStr, traceId, ex.getMessage(), ex);
        }
        return false;
    }

    public List<ChatMessageDto> getRecentMessages(String userId, String chatId, Integer chatType, Integer limit, String traceId) {
        logger.info("getRecentMessages, userId: {}, chatId: {}, chatType: {}, limit: {}, traceId: {}",
                userId, chatId, chatType, limit, traceId);
        try {
            List<ChatMessageDto> recentMessages = msgStoreService.getRecentMessages(userId, chatId, chatType, limit, traceId);
            logger.info("getRecentMessages, recentMessages.size: {}, traceId: {}", recentMessages.size(), traceId);
            return recentMessages;
        } catch (Exception ex) {
            logger.error("getRecentMessages failed, traceId: {}", traceId, ex);
            return Collections.emptyList();
        }
    }

    public List<ChatMessageDto> selectMessageByServerSeqs(String userId, String chatId, Integer chatType, Set<Long> serverSeqs, String traceId) {
        logger.info("selectMessageByServerSeqs, userId: {}, chatId: {}, chatType: {}, serverSeqs: {}, traceId: {}",
                userId, chatId, chatType, serverSeqs, traceId);
        try {
            List<ChatMessageDto> messages = msgStoreService.selectMessageByServerSeqs(userId, chatId, chatType, serverSeqs, traceId);
            logger.info("selectMessageByServerSeqs, messages.size: {}, traceId: {}", messages.size(), traceId);
            return messages;
        } catch (Exception ex) {
            logger.error("selectMessageByServerSeqs failed, traceId: {}", traceId, ex);
            return Collections.emptyList();
        }
    }
}
