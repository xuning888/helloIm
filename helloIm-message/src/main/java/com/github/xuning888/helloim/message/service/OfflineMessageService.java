package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import com.github.xuning888.helloim.message.rpc.MsgStoreSvcClient;
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
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MsgStoreSvcClient msgStoreSvcClient;

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
        redisTemplate.opsForZSet().add(offlineMsgKey, chatMessageDto, serverSeq);
        // 记录下key, 半夜清理
        addActivateChatOfflineKey(offlineMsgKey);
    }

    // 拉取离线消息
    public List<ChatMessageDto> pullOfflineMessage(PullOfflineMsgRequest request, String traceId) {
        logger.info("pullOfflineMessage, request: {}, traceId: {}", request, traceId);
        validPullOfflineMsgRequest(request, traceId, false);
        Long chatId = request.getChatId();
        Long fromUserId = request.getFromUserId();
        Long minServerSeq = request.getMinServerSeq();
        if (minServerSeq <= 0) {
            minServerSeq = 1L;
        }
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
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().rangeByScoreWithScores(msgKey, minServerSeq, maxServerSeq);

        // 转换为 List<ChatMessage>
        List<ChatMessageDto> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<Object> tuple : set) {
                ChatMessageDto message = (ChatMessageDto) tuple.getValue();
                if (message != null) {
                    // 可以在这里设置 serverSeq，确保数据完整性
                    message.setServerSeq(tuple.getScore().longValue());
                    messages.add(message);
                }
            }
        }
        // 检查是否有缺失的消息, 如果有就从MySQL补齐
        return checkMissingMessage(messages, minServerSeq, maxServerSeq, String.valueOf(fromUserId), String.valueOf(chatId), chatType, traceId);
    }

    // 拉取离线消息, 获取最近的消息
    public List<ChatMessageDto> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
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
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(msgKey, 0, size - 1);

        List<ChatMessageDto> messages = new ArrayList<>();
        if (set != null && !set.isEmpty()) {
            for (ZSetOperations.TypedTuple<Object> tuple : set) {
                ChatMessageDto message = (ChatMessageDto) tuple.getValue();
                if (message != null) {
                    message.setServerSeq(tuple.getScore().longValue());
                    messages.add(message);
                }
            }
            // 因为是倒序获取的，需要反转一下顺序
            Collections.reverse(messages);
        }

        if (CollectionUtils.isEmpty(messages)) {
            // 离线消息缓存中没有查询到任何消息, 正常情况下是不会有的
            List<ChatMessageDto> recentMessages = msgStoreSvcClient.getRecentMessages(String.valueOf(fromUserId),
                    String.valueOf(chatId), chatType, size, traceId);
            for (ChatMessageDto recentMessage : recentMessages) {
                saveOfflineMessage(recentMessage, traceId);
            }
            return recentMessages;
        }
        // 检查中间是否存在缺失
        ChatMessageDto firstMsg = messages.get(0), lastMsg = messages.get(messages.size() - 1);
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
        Cursor<Object> scan = redisTemplate.opsForSet().scan(activateChatKey, KeyScanOptions.NONE);
        List<String> clearKey = new ArrayList<>();
        try {
            while (scan.hasNext()) {
                String offlineMsgKey = (String) scan.next();
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

    private void addActivateChatOfflineKey(String offlineMsgKey) {
        String activateChatKey = RedisKeyUtils.activateChatOfflineKey(offlineMsgKey);
        redisTemplate.opsForSet().add(activateChatKey, offlineMsgKey);
    }


    private List<ChatMessageDto> checkMissingMessage(List<ChatMessageDto> messages,
                                                     Long minServerSeq, Long maxServerSeq, String userId, String chatId, Integer chatType, String traceId) {
        Set<Long> missingServerSeqs = getMissingServerSeqs(messages, minServerSeq, maxServerSeq);
        if (CollectionUtils.isEmpty(missingServerSeqs)) {
            return messages;
        }
        List<ChatMessageDto> messageDtos = msgStoreSvcClient.selectMessageByServerSeqs(String.valueOf(userId), String.valueOf(chatId), chatType, missingServerSeqs, traceId);
        if (CollectionUtils.isEmpty(messageDtos)) {
            return messages;
        }
        for (ChatMessageDto messageDto : messageDtos) {
            saveOfflineMessage(messageDto, traceId); // 维护到离线消息缓存
            messages.add(messageDto);
        }
        messages.sort(Comparator.comparing(ChatMessageDto::getServerSeq));
        return messages;
    }

    private Set<Long> getMissingServerSeqs(List<ChatMessageDto> messages, Long minServerSeq, Long maxServerSeq) {
        Set<Long> missingServerSeqs = new HashSet<>();
        for (int i = 1; i < messages.size(); i++) {
            ChatMessageDto prevMsg = messages.get(i - 1);
            ChatMessageDto curMsg = messages.get(i);
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
            ChatMessageDto firstMsg = messages.get(0);
            if (firstMsg.getServerSeq() > minServerSeq) {
                for (long seq = minServerSeq; seq < firstMsg.getServerSeq(); seq++) {
                    missingServerSeqs.add(seq);
                }
            }
            ChatMessageDto lastMsg = messages.get(messages.size() - 1);
            if (lastMsg.getServerSeq() < maxServerSeq) {
                for (long seq = lastMsg.getServerSeq() + 1; seq <= maxServerSeq; seq++) {
                    missingServerSeqs.add(seq);
                }
            }
        }
        return missingServerSeqs;
    }
}
