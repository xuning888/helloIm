package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xuning
 * @date 2025/10/7 02:10
 */
@Service
public class OfflineMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OfflineMessageService.class);

    @Autowired
    private RedisTemplate<String, ChatMessage> redisTemplate;

    // 存储离线消息
    public void saveOfflineMessage(ChatMessage chatMessage) {
        String offlineMsgKey = offlineMsgKey(chatMessage);
        redisTemplate.opsForZSet().add(offlineMsgKey, chatMessage, chatMessage.getServerSeq());
    }

    // 拉取离线消息
    public List<ChatMessage> pullOfflineMessage(PullOfflineMsgRequest request, String traceId) {
        logger.info("pullOfflineMessage, request: {}, traceId: {}", request, traceId);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Long minServerSeq = request.getMinServerSeq();
        Long maxServerSeq = request.getMaxServerSeq();
        Integer chatType = request.getChatType();

        String msgKey;
        if (ChatType.C2C.match(chatType)) {
            msgKey = c2cOfflineMsgKey(fromUserId, chatId);
        } else if (ChatType.C2G.match(chatType)) {
            msgKey = c2gOfflineMsgKey(chatId);
        } else {
            logger.error("pullOfflineMessage error, unknown chatType: {}, traceId: {}", chatType, traceId);
            return new ArrayList<>();
        }

        // 按 score 范围获取消息，带 score 信息
        Set<ZSetOperations.TypedTuple<ChatMessage>> set =
                redisTemplate.opsForZSet().rangeByScoreWithScores(msgKey, minServerSeq, maxServerSeq);

        // 转换为 List<ChatMessage>
        List<ChatMessage> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<ChatMessage> tuple : set) {
                ChatMessage message = tuple.getValue();
                if (message != null) {
                    // 可以在这里设置 serverSeq，确保数据完整性
                    message.setServerSeq(tuple.getScore().longValue());
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    public List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Integer size = request.getSize();
        Integer chatType = request.getChatType();
        String msgKey;
        if (ChatType.C2C.match(chatType)) {
            msgKey = c2cOfflineMsgKey(fromUserId, chatId);
        } else if (ChatType.C2G.match(chatType)) {
            msgKey = c2gOfflineMsgKey(chatId);
        } else {
            logger.error("getLatestOfflineMessages error, unknown chatType: {}, traceId: {}", chatType, traceId);
            return new ArrayList<>();
        }
        // 获取最新的消息
        Set<ZSetOperations.TypedTuple<ChatMessage>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(msgKey, 0, size - 1);

        List<ChatMessage> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<ChatMessage> tuple : set) {
                ChatMessage message = tuple.getValue();
                if (message != null) {
                    message.setServerSeq(tuple.getScore().longValue());
                    messages.add(message);
                }
            }
            // 因为是倒序获取的，需要反转一下顺序
            Collections.reverse(messages);
        }
        return messages;
    }

    private String offlineMsgKey(ChatMessage chatMessage) {
        Integer chatType = chatMessage.getChatType();
        if (ChatType.C2C.match(chatType)) {
            return c2cOfflineMsgKey(chatMessage.getMsgFrom(), chatMessage.getMsgTo());
        } else if (ChatType.C2G.match(chatType)) {
            return c2gOfflineMsgKey(chatMessage.getGroupId());
        } else {
            throw new IllegalArgumentException("Unknown chatType: " + chatType);
        }
    }

    private String c2cOfflineMsgKey(Long msgFrom, Long msgTo) {
        Long smallerId = Math.min(msgFrom, msgTo);
        Long largerId = Math.max(msgFrom, msgTo);
        return "offline_message_" + ChatType.C2C + "_" + smallerId + "_" + largerId;
    }

    private String c2gOfflineMsgKey(Long groupId) {
        return "offline_message_" + ChatType.C2G + "_" + groupId;
    }
}
