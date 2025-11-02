package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import com.github.xuning888.helloim.message.api.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xuning
 * @date 2025/10/7 02:10
 */
@Service
public class OfflineMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OfflineMessageService.class);

    @Autowired
    private RedisTemplate<String, Msg.ChatMessage> redisTemplate;

    // 存储离线消息
    public void saveOfflineMessage(Msg.ChatMessage chatMessage, String traceId) {
        if (chatMessage == null) {
            logger.error("saveOfflineMessage chatMessage is null traceId: {}", traceId);
            return;
        }
        Long fromUserId = chatMessage.getMsgFrom();
        Long toUserId = chatMessage.getMsgTo();
        Long groupId = chatMessage.getGroupId();
        int chatType = chatMessage.getChatType();
        long msgId = chatMessage.getMsgId();
        long serverSeq = chatMessage.getServerSeq();
        String offlineMsgKey = RedisKeyUtils.offlineMsgKey(fromUserId, toUserId, groupId, chatType);
        logger.info("saveOfflineMessage, msgId: {}, serverSeq: {}, offlineMsgKey: {}", msgId, serverSeq, offlineMsgKey);
        // TODO 策略: 单聊存储近100条消息, 群聊记录下key, 半夜清理
        redisTemplate.opsForZSet().add(offlineMsgKey, chatMessage, serverSeq);
    }

    // 拉取离线消息
    public List<Msg.ChatMessage> pullOfflineMessage(Msg.PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        logger.info("pullOfflineMessage, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, false);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        long minServerSeq = request.getMinServerSeq();
        long maxServerSeq = request.getMaxServerSeq();
        Integer chatType = request.getChatType();
        String msgKey;
        if (ChatType.C2C.match(chatType)) {
            msgKey = RedisKeyUtils.c2cOfflineMsgKey(fromUserId, chatId);
        } else if (ChatType.C2G.match(chatType)) {
            msgKey = RedisKeyUtils.c2gOfflineMsgKey(chatId);
        } else {
            logger.error("pullOfflineMessage error, unknown chatType: {}, traceId: {}", chatType, traceId);
            return new ArrayList<>();
        }
        // 按 score 范围获取消息，带 score 信息
        Set<ZSetOperations.TypedTuple<Msg.ChatMessage>> set = redisTemplate.opsForZSet().rangeByScoreWithScores(msgKey, minServerSeq, maxServerSeq);

        // 转换为 List<ChatMessage>
        List<Msg.ChatMessage> messages = null;
        if (set != null && !set.isEmpty()) {
            messages = extratChatMessage(set);
        } else {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public List<Msg.ChatMessage> getLatestOfflineMessages(Msg.PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, true);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        int size = request.getSize();
        if (size <= 0) {
            size = 50;
        }
        Integer chatType = request.getChatType();
        String msgKey;
        if (ChatType.C2C.match(chatType)) {
            msgKey = RedisKeyUtils.c2cOfflineMsgKey(fromUserId, chatId);
        } else if (ChatType.C2G.match(chatType)) {
            msgKey = RedisKeyUtils.c2gOfflineMsgKey(chatId);
        } else {
            logger.error("getLatestOfflineMessages error, unknown chatType: {}, traceId: {}", chatType, traceId);
            return Collections.emptyList();
        }
        // 获取最新的消息
        Set<ZSetOperations.TypedTuple<Msg.ChatMessage>> set = redisTemplate.opsForZSet().reverseRangeWithScores(msgKey, 0, size - 1);

        List<Msg.ChatMessage> messages = null;
        if (set != null && !set.isEmpty()) {
            messages = extratChatMessage(set);
            // 因为是倒序获取的，需要反转一下顺序
            Collections.reverse(messages);
        } else {
            messages = Collections.emptyList();
        }
        return messages;
    }


    private void validPullOfflineMsgRequest(Msg.PullOfflineMsgRequest request, String traceId, boolean last) {
        if (Objects.isNull(request)) {
            logger.error("validPullOfflineMsgRequest request is null, traceId: {}", traceId);
            throw new IllegalArgumentException("request is null");
        }
        long minServerSeq = request.getMinServerSeq();
        long maxServerSeq = request.getMaxServerSeq();
        if (minServerSeq < 0) {
            logger.error("validPullOfflineMsgRequest minServerSeq is invalid, minSvrSeq: {}, traceId: {}", minServerSeq, traceId);
            throw new IllegalArgumentException("minServerSeq is invalid");
        }
        if (maxServerSeq < 0) {
            logger.error("validPullOfflineMsgRequest maxServerSeq is invalid, maxSvrSeq: {}, traceId: {}", maxServerSeq, traceId);
            throw new IllegalArgumentException("maxServerSeq is invalid");
        }
    }

    private List<Msg.ChatMessage> extratChatMessage(Set<ZSetOperations.TypedTuple<Msg.ChatMessage>> set) {
        List<Msg.ChatMessage> messages = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Msg.ChatMessage> tuple : set) {
            Msg.ChatMessage message = tuple.getValue();
            if (message != null) {
                message = message.toBuilder().setServerSeq(tuple.getScore().longValue()).build();
                messages.add(message);
            }
        }
        return messages;
    }
}
