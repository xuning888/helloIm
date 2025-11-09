package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
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
    private RedisTemplate<String, ChatMessageDto> redisTemplate;

    // 存储离线消息
    public void saveOfflineMessage(ChatMessageDto chatMessageDto, String traceId) {
        if (chatMessageDto == null) {
            logger.error("saveOfflineMessage chatMessage is null traceId: {}", traceId);
            return;
        }
        Long fromUserId = chatMessageDto.getMsgFrom();
        Long toUserId = chatMessageDto.getMsgTo();
        Long groupId = chatMessageDto.getGroupId();
        Integer chatType = chatMessageDto.getChatType();
        Long msgId = chatMessageDto.getMsgId();
        Long serverSeq = chatMessageDto.getServerSeq();
        String offlineMsgKey = RedisKeyUtils.offlineMsgKey(fromUserId, toUserId, groupId, chatType);
        logger.info("saveOfflineMessage, msgId: {}, serverSeq: {}, offlineMsgKey: {}", msgId, serverSeq, offlineMsgKey);
        // TODO 策略: 单聊存储近100条消息, 群聊记录下key, 半夜清理
        redisTemplate.opsForZSet().add(offlineMsgKey, chatMessageDto, serverSeq);
    }

    // 拉取离线消息
    public List<ChatMessageDto> pullOfflineMessage(PullOfflineMsgRequest request, String traceId) {
        logger.info("pullOfflineMessage, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, false);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Long minServerSeq = request.getMinServerSeq();
        Long maxServerSeq = request.getMaxServerSeq();
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
        Set<ZSetOperations.TypedTuple<ChatMessageDto>> set =
                redisTemplate.opsForZSet().rangeByScoreWithScores(msgKey, minServerSeq, maxServerSeq);

        // 转换为 List<ChatMessage>
        List<ChatMessageDto> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<ChatMessageDto> tuple : set) {
                ChatMessageDto message = tuple.getValue();
                if (message != null) {
                    // 可以在这里设置 serverSeq，确保数据完整性
                    message.setServerSeq(tuple.getScore().longValue());
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    public List<ChatMessageDto> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, true);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Integer size = request.getSize();
        Integer chatType = request.getChatType();
        String msgKey;
        if (ChatType.C2C.match(chatType)) {
            msgKey = RedisKeyUtils.c2cOfflineMsgKey(fromUserId, chatId);
        } else if (ChatType.C2G.match(chatType)) {
            msgKey = RedisKeyUtils.c2gOfflineMsgKey(chatId);
        } else {
            logger.error("getLatestOfflineMessages error, unknown chatType: {}, traceId: {}", chatType, traceId);
            return new ArrayList<>();
        }
        // 获取最新的消息
        Set<ZSetOperations.TypedTuple<ChatMessageDto>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(msgKey, 0, size - 1);

        List<ChatMessageDto> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<ChatMessageDto> tuple : set) {
                ChatMessageDto message = tuple.getValue();
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


    private void validPullOfflineMsgRequest(PullOfflineMsgRequest request, String traceId, boolean last) {
        if (Objects.isNull(request)) {
            logger.error("validPullOfflineMsgRequest request is null, traceId: {}", traceId);
            throw new IllegalArgumentException("request is null");
        }
        if (Objects.isNull(request.getChatId())) {
            logger.error("validPullOfflineMsgRequest chatId is null, traceId: {}", traceId);
            throw new IllegalArgumentException("chatId is null");
        }
        if (Objects.isNull(request.getFromUserId())) {
            logger.error("validPullOfflineMsgRequest fromUserId is null, traceId: {}", traceId);
            throw new IllegalArgumentException("fromUserId is null");
        }
        if (Objects.isNull(request.getChatType())) {
            logger.error("validPullOfflineMsgRequest chatType is null, traceId: {}", traceId);
            throw new IllegalArgumentException("chatType is null");
        }
        if (last) {
            if (Objects.isNull(request.getSize())) {
                logger.warn("validPullOfflineMsgRequest size is null, traceId: {}", traceId);
                request.setSize(PullOfflineMsgRequest.DEFAULT_SIZE);
            }
        } else {
            Long minServerSeq = request.getMinServerSeq();
            Long maxServerSeq = request.getMaxServerSeq();
            if (Objects.isNull(minServerSeq) || minServerSeq < 0) {
                logger.error("validPullOfflineMsgRequest minServerSeq is invalid, minSvrSeq: {}, traceId: {}",
                        minServerSeq, traceId);
                throw new IllegalArgumentException("minServerSeq is invalid");
            }
            if (Objects.isNull(maxServerSeq) || maxServerSeq < 0) {
                logger.error("validPullOfflineMsgRequest maxServerSeq is invalid, maxSvrSeq: {}, traceId: {}",
                        maxServerSeq, traceId);
                throw new IllegalArgumentException("maxServerSeq is invalid");
            }
        }
    }
}
