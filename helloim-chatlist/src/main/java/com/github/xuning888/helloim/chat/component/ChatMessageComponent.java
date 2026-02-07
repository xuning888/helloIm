package com.github.xuning888.helloim.chat.component;

import com.github.xuning888.helloim.api.dto.ImChatDto;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.chat.rpc.MsgStoreRpc;
import com.github.xuning888.helloim.chat.utils.LastMessageUtils;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/11/9 21:34
 */
@Component
public class ChatMessageComponent {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageComponent.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MsgStoreRpc msgStoreRpc;


    public void updateLastChatMessage(String userId, String chatId, ChatMessage chatMessage, String traceId) {
        Integer chatType = chatMessage.getChatType();
        try {
            if (ChatType.C2C.match(chatType)) {
                updateC2cLastMessage(userId, chatId, chatMessage, traceId);
            } else if (ChatType.C2G.match(chatType)) {
                updateC2gLastMessage(chatMessage, traceId);
            } else {
                logger.error("updateLastChatMessage 未知的会话类型, chatMessage: {}, traceId: {}", chatMessage, traceId);
            }
        } catch (Exception ex) {
            logger.error("updateLastChatMessage 未知异常 traceId: {}", traceId, ex);
        }
    }

    public ChatMessage getLastMessage(String userId, String chatId, Integer chatType, String traceId) {
        ChatMessage lastMessage = getLastMessageFromCache(userId, chatId, chatType, traceId);
        if (lastMessage != null) {
            return lastMessage;
        }
        ChatMessage chatMessage = msgStoreRpc.lastMessage(userId, chatId, chatType, traceId);
        if (chatMessage == null) {
            // 有可能查询不到
            return null;
        }
        updateLastChatMessage(userId, chatId, chatMessage, traceId);
        return chatMessage;
    }

    public Map<String, ChatMessage> multiLastMessages(String userId, List<ImChatDto> imChatDtoList, String traceId) {
        if (CollectionUtils.isEmpty(imChatDtoList)) {
            return new HashMap<>();
        }
        Map<String, ChatMessage> messageDtoMap = new HashMap<>();
        for (ImChatDto imChatDto : imChatDtoList) {
            Long chatId = imChatDto.getChatId();
            Integer chatType = imChatDto.getChatType();
            ChatMessage lastMessage = getLastMessage(userId, String.valueOf(chatId), chatType, traceId);
            String key = lastMessageKey(userId, String.valueOf(chatId), chatType);
            messageDtoMap.put(key, lastMessage);
        }
        return messageDtoMap;
    }

    private ChatMessage getLastMessageFromCache(String userId, String chatId, Integer chatType, String traceId) {
        String value = null;
        String key = null;
        if (ChatType.C2C.match(chatType)) {
            key = RedisKeyUtils.c2cLastMessageKey(userId, chatId);
            value = this.redisTemplate.opsForValue().get(key);
        } else if (ChatType.C2G.match(chatType)) {
            key = RedisKeyUtils.c2gLastMessageKey(Long.parseLong(chatId));
            Object obj = this.redisTemplate.opsForHash().get(key, chatId);
            if (obj != null) {
                value = (String) obj;
            }
        } else {
            logger.error("getLastMessage, 未知的会话类型， userId: {}, chatId: {}, chatType: {}, traceId: {}", userId, chatId, chatType, traceId);
            throw new IllegalArgumentException("未知的会话类型: " + chatType);
        }
        if (value == null) {
            logger.warn("getLastMessage value is null, key: {}, traceId: {}", key, traceId);
            return null;
        }
        return ProtobufUtils.fromJson(value, ChatMessage.newBuilder());
    }

    private void updateC2cLastMessage(String userId, String toUserId, ChatMessage chatMessage, String traceId) {
        LastMessageUtils.updateC2CLastMessage(redisTemplate, userId, toUserId, chatMessage, traceId);
    }

    private void updateC2gLastMessage(ChatMessage chatMessage, String traceId) {
        String key = RedisKeyUtils.c2gLastMessageKey(chatMessage.getGroupId());
        Long msgId = chatMessage.getMsgId();
        Long serverSeq = chatMessage.getServerSeq();
        logger.info("updateC2gLastMessage key: {}, msgId: {}, serverSeq: {} traceId: {}", key, msgId, serverSeq, traceId);
        String json = ProtobufUtils.toJson(chatMessage);
        this.redisTemplate.opsForHash().put(key, chatMessage.getGroupIdStr(), json);
    }

    public String lastMessageKey(String userId, String chatId, Integer chatType) {
        if (ChatType.C2C.match(chatType)) {
            return RedisKeyUtils.c2cLastMessageKey(userId, String.valueOf(chatId));
        } else if (ChatType.C2G.match(chatType)) {
            return RedisKeyUtils.c2gLastMessageKey(Long.parseLong(chatId));
        } else {
            throw new IllegalArgumentException("未知的会话类型: " + chatType);
        }
    }
}
