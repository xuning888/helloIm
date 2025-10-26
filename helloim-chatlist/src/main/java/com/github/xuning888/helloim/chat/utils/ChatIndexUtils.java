package com.github.xuning888.helloim.chat.utils;

import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xuning
 * @date 2025/10/25 14:15
 */
public class ChatIndexUtils {

    public static final Logger logger = LoggerFactory.getLogger(ChatIndexUtils.class);

    public static ImChat getChat(RedisTemplate redisTemplate, String userId, String chatId, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(userId);
        Object value = redisTemplate.opsForHash().get(chatIndexKey, chatId);
        if (value == null) {
            logger.warn("getChat is null, userId: {}, chatId: {}, traceId: {}", userId, chatId, traceId);
            return null;
        }
        return (ImChat) value;
    }

    public static void putChat(RedisTemplate redisTemplate, ImChat imChat, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(String.valueOf(imChat.getUserId()));
        logger.info("putChat key: {}, traceId: {}", chatIndexKey, traceId);
        redisTemplate.opsForHash().put(chatIndexKey, imChat.getChatId(), imChat);
    }
}
