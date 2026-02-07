package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import com.github.xuning888.helloim.message.rpc.MsgStoreRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.KeyScanOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuning
 * @date 2025/10/7 02:10
 */
@Service
public class OfflineMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OfflineMessageService.class);

    private final long offlineMessageCount = 100;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private MsgStoreRpc msgStoreRpc;

    // 存储离线消息
    public void saveOfflineMessage(ChatMessage chatMessage, String traceId) {
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
        String json = ProtobufUtils.toJson(chatMessage);
        redisTemplate.opsForZSet().add(offlineMsgKey, json, serverSeq);
        // 记录下key, 半夜清理
        addActivateChatOfflineKey(offlineMsgKey);
    }

    // 拉取离线消息
    public List<ChatMessage> pullOfflineMessage(PullOfflineMsgRequest request, String traceId) {
        logger.info("pullOfflineMessage, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, false);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        long minServerSeq = request.getMinServerSeq();
        if (minServerSeq <= 0) {
            minServerSeq = 1L;
        }
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
        Set<ZSetOperations.TypedTuple<String>> set =
                redisTemplate.opsForZSet().rangeByScoreWithScores(msgKey, minServerSeq, maxServerSeq);

        // 转换为 List<ChatMessage>
        List<ChatMessage> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                ChatMessage message = ProtobufUtils.fromJson(tuple.getValue(), ChatMessage.newBuilder());
                if (message != null) {
                    messages.add(message);
                }
            }
        }
        // 检查是否有缺失的消息, 如果有就从MySQL补齐
        return checkMissingMessage(messages, minServerSeq, maxServerSeq, String.valueOf(fromUserId), String.valueOf(chatId), chatType, traceId);
    }

    // 拉取离线消息, 获取最近的消息
    public List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, true);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Integer size = request.getSize();
        if (size > 50) {
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
            return new ArrayList<>();
        }
        // 获取最新的消息
        Set<ZSetOperations.TypedTuple<String>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(msgKey, 0, size - 1);

        List<ChatMessage> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                String value = tuple.getValue();
                ChatMessage message = ProtobufUtils.fromJson(value, ChatMessage.newBuilder());
                if (message != null) {
                    messages.add(message);
                }
            }
            // 因为是倒序获取的，需要反转一下顺序
            Collections.reverse(messages);
        }

        if (CollectionUtils.isEmpty(messages)) {
            // 离线消息缓存中没有查询到任何消息, 正常情况下是不会有的
            List<ChatMessage> recentMessages = msgStoreRpc.getRecentMessages(String.valueOf(fromUserId),
                    String.valueOf(chatId), chatType, size, traceId);
            for (ChatMessage recentMessage : recentMessages) {
                saveOfflineMessage(recentMessage, traceId);
            }
            return recentMessages;
        }
        // 检查中间是否存在缺失
        ChatMessage firstMsg = messages.get(0), lastMsg = messages.get(messages.size() - 1);
        return checkMissingMessage(messages, firstMsg.getServerSeq(), lastMsg.getServerSeq(),
                String.valueOf(fromUserId), String.valueOf(chatId), chatType, traceId);
    }

    public void cleanOfflineMessage() {
        for (int i = 0; i < RedisKeyUtils.shardValue; i++) {
            String activateChatKey = RedisKeyUtils.activateChatOfflineKey(i);
            scanOfflineMessageKey(activateChatKey);
        }
    }

    public boolean doCleanOfflineMessageKey(String offlineMessageKey) {
        Long count = redisTemplate.opsForZSet().zCard(offlineMessageKey); // 先统计数量
        // 数量都查不到, 说明没有消息
        if (count == null) {
            logger.info("doCleanOfflineMessageKey offlineMessageKey: {}, count is null", offlineMessageKey);
            return true;
        }
        logger.info("doCleanOfflineMessageKey offlineMessageKey: {}, count: {}", offlineMessageKey, count);
        // 消息数量小于等于限制的数量, 不处理
        if (count <= offlineMessageCount) {
            return true;
        }
        // ZREMRANGEBYRANK key start end
        // O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements removed by the operation.
        // 小批量删除, 避免阻塞
        int delCount = 100;
        long removeCount = count - offlineMessageCount; // 需要删除的数量
        logger.info("doCleanOfflineMessageKey offlineMessageKey: {}, removeCount: {}", offlineMessageKey, removeCount);
        long totalRemoveCount = 0;
        while (removeCount > 0) {
            if (delCount > removeCount) {
                delCount = (int) removeCount;
            }
            Long actualRemoveCount = redisTemplate.opsForZSet().removeRange(offlineMessageKey, 0, delCount - 1);
            if (actualRemoveCount == null) {
                break;
            }
            totalRemoveCount += actualRemoveCount;
            removeCount -= actualRemoveCount;
        }
        logger.info("doCleanOfflineMessageKey offlineMessageKey: {}, totalRemoveCount: {}", offlineMessageKey, totalRemoveCount);
        return true;
    }

    private void scanOfflineMessageKey(String activateChatKey) {
        Cursor<String> scan = redisTemplate.opsForSet().scan(activateChatKey, KeyScanOptions.NONE);
        List<String> clearKey = new ArrayList<>();
        try {
            while (scan.hasNext()) {
                String offlineMsgKey = scan.next();
                try {
                    if (doCleanOfflineMessageKey(offlineMsgKey)) {
                        clearKey.add(offlineMsgKey);
                    }
                } catch (Exception ex) {
                    logger.error("scanOfflineMessageKey doCleanOfflineMessageKey offlineMsgKey: {}", offlineMsgKey, ex);
                }
            }
        } finally {
            scan.close();
        }
        for (String key : clearKey) {
            redisTemplate.opsForSet().remove(activateChatKey, key);
        }
    }

    private void validPullOfflineMsgRequest(PullOfflineMsgRequest request, String traceId, boolean last) {
        if (Objects.isNull(request)) {
            logger.error("validPullOfflineMsgRequest request is null, traceId: {}", traceId);
            throw new IllegalArgumentException("request is null");
        }
        if (last) {
            int size = request.getSize();
            if (size < 0) {
                logger.warn("validPullOfflineMsgRequest size is invalid, traceId: {}", traceId);
                throw new IllegalArgumentException("size is invalid");
            }
        } else {
            long minServerSeq = request.getMinServerSeq();
            long maxServerSeq = request.getMaxServerSeq();
            if (minServerSeq < 0) {
                logger.error("validPullOfflineMsgRequest minServerSeq is invalid, minSvrSeq: {}, traceId: {}",
                        minServerSeq, traceId);
                throw new IllegalArgumentException("minServerSeq is invalid");
            }
            if (maxServerSeq < 0) {
                logger.error("validPullOfflineMsgRequest maxServerSeq is invalid, maxSvrSeq: {}, traceId: {}",
                        maxServerSeq, traceId);
                throw new IllegalArgumentException("maxServerSeq is invalid");
            }
        }
    }

    private void addActivateChatOfflineKey(String offlineMsgKey) {
        String activateChatKey = RedisKeyUtils.activateChatOfflineKey(offlineMsgKey);
        redisTemplate.opsForSet().add(activateChatKey, offlineMsgKey);
    }


    private List<ChatMessage> checkMissingMessage(List<ChatMessage> messages,
                                                     Long minServerSeq, Long maxServerSeq, String userId, String chatId, Integer chatType, String traceId) {
        Set<Long> missingServerSeqs = getMissingServerSeqs(messages, minServerSeq, maxServerSeq);
        if (CollectionUtils.isEmpty(missingServerSeqs)) {
            return messages;
        }
        List<ChatMessage> messageDtos = msgStoreRpc.selectMessageByServerSeqs(String.valueOf(userId), String.valueOf(chatId), chatType, missingServerSeqs, traceId);
        if (CollectionUtils.isEmpty(messageDtos)) {
            return messages;
        }
        for (ChatMessage messageDto : messageDtos) {
            saveOfflineMessage(messageDto, traceId); // 维护到离线消息缓存
            messages.add(messageDto);
        }
        messages.sort(Comparator.comparing(ChatMessage::getServerSeq));
        return messages;
    }

    private Set<Long> getMissingServerSeqs(List<ChatMessage> messages, Long minServerSeq, Long maxServerSeq) {
        Set<Long> missingServerSeqs = new HashSet<>();
        for (int i = 1; i < messages.size(); i++) {
            ChatMessage prevMsg = messages.get(i - 1);
            ChatMessage curMsg = messages.get(i);
            // 检查相邻消息的serverSeq是否连续
            long diff = curMsg.getServerSeq() - prevMsg.getServerSeq();
            if (diff > 1) {
                for (long seq = prevMsg.getServerSeq() + 1; seq < curMsg.getServerSeq(); seq++) {
                    missingServerSeqs.add(seq);
                }
            }
        }
        // 没有从离线消息中获取到任何消息
        if (messages.isEmpty()) {
            for (long seq = minServerSeq; seq <= maxServerSeq; seq++) {
                missingServerSeqs.add(seq);
            }
        } else {
            // 检查首尾
            ChatMessage firstMsg = messages.get(0);
            if (firstMsg.getServerSeq() > minServerSeq) {
                for (long seq = minServerSeq; seq < firstMsg.getServerSeq(); seq++) {
                    missingServerSeqs.add(seq);
                }
            }
            ChatMessage lastMsg = messages.get(messages.size() - 1);
            if (lastMsg.getServerSeq() < maxServerSeq) {
                for (long seq = lastMsg.getServerSeq() + 1; seq <= maxServerSeq; seq++) {
                    missingServerSeqs.add(seq);
                }
            }
        }
        return missingServerSeqs;
    }
}
