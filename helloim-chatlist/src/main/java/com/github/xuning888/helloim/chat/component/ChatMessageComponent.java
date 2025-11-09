package com.github.xuning888.helloim.chat.component;

import com.github.xuning888.helloim.chat.utils.ChatUtils;
import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.ImChatDto;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        Map<String, ChatMessageDto> chatMessageMap = new HashMap<>();
        // 按照chatType 分组
        Map<Integer, List<ImChatDto>> groupByChatType = imChatDtoList.stream().collect(Collectors.groupingBy(ImChatDto::getChatType));
        for (Map.Entry<Integer, List<ImChatDto>> entry : groupByChatType.entrySet()) {
            Integer chatType = entry.getKey();
            List<ImChatDto> imChatDtos = entry.getValue();
            if (ChatType.C2C.match(chatType)) {
                String key = RedisKeyUtils.c2cLastMessageKey(userId);
                List<String> chatIds = imChatDtos.stream().map(item -> String.valueOf(item.getChatId())).collect(Collectors.toList());
                List<ChatMessageDto> list = (List<ChatMessageDto>) hashOperations.multiGet(key, chatIds);
                if (CollectionUtils.isEmpty(list)) {
                    for (String chatId : chatIds) {
                        String chatKey = ChatUtils.chatKey(chatId, chatType);
                        chatMessageMap.put(chatKey, null);
                    }
                } else {
                    Map<String, ChatMessageDto> map = list.stream().collect(Collectors.toMap(ChatMessageDto::getChatIdStr, x -> x, (x, y) -> x));
                    for (String chatId : chatIds) {
                        String chatKey = ChatUtils.chatKey(chatId, chatType);
                        ChatMessageDto chatMessageDto = map.get(chatId);
                        chatMessageMap.put(chatKey, chatMessageDto);
                    }
                }
            } else if (ChatType.C2G.match(chatType)) {
                Map<String, List<Long>> keysGroupIndex = imChatDtos.stream().map(ImChatDto::getChatId).collect(Collectors.groupingBy(RedisKeyUtils::c2gLastMessageKey));
                for (Map.Entry<String, List<Long>> subEntry : keysGroupIndex.entrySet()) {
                    String key = subEntry.getKey();
                    List<String> groupIds = subEntry.getValue().stream().map(String::valueOf).collect(Collectors.toList());
                    List<ChatMessageDto> list = (List<ChatMessageDto>) hashOperations.multiGet(key, groupIds);
                    if (CollectionUtils.isEmpty(list)) {
                        for (String groupId : groupIds) {
                            String chatKey = ChatUtils.chatKey(String.valueOf(groupId), chatType);
                            chatMessageMap.put(chatKey, null);
                        }
                    } else {
                        Map<String, ChatMessageDto> map = list.stream().collect(Collectors.toMap(ChatMessageDto::getChatIdStr, x -> x, (x, y) -> x));
                        for (String groupId : groupIds) {
                            ChatMessageDto chatMessageDto = map.get(String.valueOf(groupId));
                            String chatKey = ChatUtils.chatKey(String.valueOf(groupId), chatType);
                            chatMessageMap.put(chatKey, chatMessageDto);
                        }
                    }
                }
            } else {
                logger.error("multiLastMessages 未知的会话类型, traceId: {}", traceId);
            }
        }
        // chatId --> chatMessage
        Map<String, ChatMessageDto> result = new HashMap<>();
        // 从缓存没拉到的数据从db拉
        Set<String> keys = chatMessageMap.keySet();
        for (String chatKey : keys) {
            ChatMessageDto chatMessageDto = chatMessageMap.get(chatKey);
            if (chatMessageDto != null) {
                result.put(chatMessageDto.getChatIdStr(), chatMessageDto);
            }
            Pair<String, Integer> subInfo = ChatUtils.getSubInfo(chatKey);
            if (subInfo == null) {
                continue;
            }
            String chatId = subInfo.getLeft();
            Integer chatType = subInfo.getRight();
            chatMessageDto = this.msgStoreService.lastMessage(userId, chatId, chatType, traceId);
            if (chatMessageDto != null) {
                result.put(chatMessageDto.getChatIdStr(), chatMessageDto);
                this.updateLastChatMessage(userId, chatId, chatMessageDto, traceId);
            }
        }
        return result;
    }

    private ChatMessageDto getLastMessageFromCache(String userId, String chatId, Integer chatType, String traceId) {
        Object value = null;
        String key = null;
        if (ChatType.C2C.match(chatType)) {
            key = RedisKeyUtils.c2cLastMessageKey(userId);
            value = this.redisTemplate.opsForHash().get(key, chatId);
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
        String key = RedisKeyUtils.c2cLastMessageKey(userId);
        logger.info("updateC2cLastMessage key: {}, traceId: {}", key, traceId);
        this.redisTemplate.opsForHash().put(key, toUserId, chatMessageDto);
    }

    private void updateC2gLastMessage(ChatMessageDto chatMessageDto, String traceId) {
        String key = RedisKeyUtils.c2gLastMessageKey(chatMessageDto.getGroupId());
        logger.info("updateC2gLastMessage key: {}, traceId: {}", key, traceId);
        this.redisTemplate.opsForHash().put(key, chatMessageDto.getGroupIdStr(), chatMessageDto);
    }
}
