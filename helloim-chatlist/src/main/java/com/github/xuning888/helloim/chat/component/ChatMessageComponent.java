package com.github.xuning888.helloim.chat.component;

import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.ImChatDto;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.apache.dubbo.config.annotation.DubboReference;
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
    private RedisTemplate redisTemplate;

    @DubboReference
    private MsgStoreService msgStoreService;


    public void updateLastChatMessage(String userId, String chatId, ChatMessageDto chatMessageDto, String traceId) {
        Integer chatType = chatMessageDto.getChatType();
        try {
            if (ChatType.C2C.match(chatType)) {
                updateC2cLastMessage(userId, chatId, chatMessageDto, traceId);
            } else if (ChatType.C2G.match(chatType)) {
                updateC2gLastMessage(chatMessageDto, traceId);
            } else {
                logger.error("updateLastChatMessage 未知的会话类型, chatMessage: {}, traceId: {}", chatMessageDto, traceId);
            }
        } catch (Exception ex) {
            logger.error("updateLastChatMessage 未知异常 traceId: {}", traceId, ex);
        }
    }

    public ChatMessageDto getLastMessage(String userId, String chatId, Integer chatType, String traceId) {
        ChatMessageDto lastMessage = getLastMessageFromCache(userId, chatId, chatType, traceId);
        if (lastMessage != null) {
            return lastMessage;
        }
        ChatMessageDto chatMessageDto = msgStoreService.lastMessage(userId, chatId, chatType, traceId);
        if (chatMessageDto == null) {
            // 有可能查询不到
            return null;
        }
        updateLastChatMessage(userId, chatId, chatMessageDto, traceId);
        return chatMessageDto;
    }

    public Map<String, ChatMessageDto> multiLastMessages(String userId, List<ImChatDto> imChatDtoList, String traceId) {
        if (CollectionUtils.isEmpty(imChatDtoList)) {
            return new HashMap<>();
        }
        Map<String, ChatMessageDto> messageDtoMap = new HashMap<>();
        for (ImChatDto imChatDto : imChatDtoList) {
            Long chatId = imChatDto.getChatId();
            Integer chatType = imChatDto.getChatType();
            ChatMessageDto lastMessage = getLastMessage(userId, String.valueOf(chatId), chatType, traceId);
            String key = lastMessageKey(userId, String.valueOf(chatId), chatType);
            messageDtoMap.put(key, lastMessage);
        }
        return messageDtoMap;
    }

    private ChatMessageDto getLastMessageFromCache(String userId, String chatId, Integer chatType, String traceId) {
        Object value = null;
        String key = null;
        if (ChatType.C2C.match(chatType)) {
            key = RedisKeyUtils.c2cLastMessageKey(userId, chatId);
            value = this.redisTemplate.opsForValue().get(key);
        } else if (ChatType.C2G.match(chatType)) {
            key = RedisKeyUtils.c2gLastMessageKey(Long.parseLong(chatId));
            value = this.redisTemplate.opsForHash().get(key, chatId);
        } else {
            logger.error("getLastMessage, 未知的会话类型， userId: {}, chatId: {}, chatType: {}, traceId: {}", userId, chatId, chatType, traceId);
            throw new IllegalArgumentException("未知的会话类型: " + chatType);
        }
        if (value == null) {
            logger.warn("getLastMessage value is null, key: {}, traceId: {}", key, traceId);
            return null;
        }
        return (ChatMessageDto) value;
    }

    private void updateC2cLastMessage(String userId, String toUserId, ChatMessageDto chatMessageDto, String traceId) {
        String key = RedisKeyUtils.c2cLastMessageKey(userId, toUserId);
        Long msgId = chatMessageDto.getMsgId();
        Long serverSeq = chatMessageDto.getServerSeq();
        logger.info("updateC2cLastMessage key: {}, msgId: {}, serverSeq: {}, traceId: {}", key, msgId, serverSeq, traceId);
        this.redisTemplate.opsForValue().set(key, chatMessageDto);
    }

    private void updateC2gLastMessage(ChatMessageDto chatMessageDto, String traceId) {
        String key = RedisKeyUtils.c2gLastMessageKey(chatMessageDto.getGroupId());
        Long msgId = chatMessageDto.getMsgId();
        Long serverSeq = chatMessageDto.getServerSeq();
        logger.info("updateC2gLastMessage key: {}, msgId: {}, serverSeq: {} traceId: {}", key, msgId, serverSeq, traceId);
        this.redisTemplate.opsForHash().put(key, chatMessageDto.getGroupIdStr(), chatMessageDto);
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
