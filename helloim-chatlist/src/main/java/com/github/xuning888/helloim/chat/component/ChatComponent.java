package com.github.xuning888.helloim.chat.component;

import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;
import com.github.xuning888.helloim.chat.rpc.ChatStoreRpc;
import com.github.xuning888.helloim.chat.utils.ThreadPoolUtils;
import com.github.xuning888.helloim.contract.api.service.UserGroupService;
import com.github.xuning888.helloim.contract.contant.ChatSubStatus;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuning
 * @date 2025/11/9 19:59
 */
@Component
public class ChatComponent {

    private static final Logger logger = LoggerFactory.getLogger(ChatComponent.class);
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ChatStoreRpc chatStoreRpc;
    @DubboReference
    private UserGroupService userGroupService;
    @Resource
    private ChatMessageComponent chatMessageComponent;

    public ImChat createImChatDto(Long userId, Long chatId, Integer chatType, String traceId) {
        ImChat.Builder builder = ImChat.newBuilder();
        builder.setUserId(userId);
        builder.setChatId(chatId);
        builder.setChatType(chatType);
        builder.setChatTop(false);
        builder.setChatMute(false);
        builder.setChatDel(false);
        builder.setUpdateTimestamp(System.currentTimeMillis());
        builder.setDelTimestamp(System.currentTimeMillis());
        if (ChatType.C2G.match(chatType)) {
            Date userJoinGroupTime = userGroupService.getUserJoinGroupTime(chatId, String.valueOf(userId), traceId);
            Long updateTimestamp = null;
            if (userJoinGroupTime == null) {
                updateTimestamp = System.currentTimeMillis();
            } else {
                updateTimestamp = userJoinGroupTime.getTime();
                builder.setSubStatus(ChatSubStatus.IN_GROUP.getCode());
            }
            builder.setUpdateTimestamp(updateTimestamp);
        }
        ChatMessage lastMessage = chatMessageComponent.getLastMessage(userId.toString(), chatId.toString(), chatType, traceId);
        if (lastMessage != null) {
            updateChatDto(builder, lastMessage);
        }
        return builder.build();
    }

    /**
     * 获取会话
     * 1. 并根据会话的最后一条消息更新会话版本
     * 2. 如果会话版本发生变化, 更新缓存, 然后异步更新数据库
     */
    public ImChat getChat(String userId, String chatId, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(userId);
        Object value = redisTemplate.opsForHash().get(chatIndexKey, chatId);
        if (value == null) {
            logger.warn("getChat is null, userId: {}, chatId: {}, traceId: {}", userId, chatId, traceId);
            return null;
        }
        String jsonStr = String.valueOf(value);
        ImChat imChat = ProtobufUtils.fromJson(jsonStr, ImChat.newBuilder());
        ChatMessage lastMessage = this.chatMessageComponent.getLastMessage(userId, chatId, imChat.getChatType(), traceId);
        if (lastMessage != null) {
            ImChat.Builder builder = imChat.toBuilder();
            if (updateChatDto(builder, lastMessage)) {
                imChat = builder.build();
                batchUpdateChat(Collections.singleton(imChat), traceId);
            }
        }
        return imChat;
    }

    public void putChat(ImChat imChat, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(String.valueOf(imChat.getUserId()));
        logger.info("putChat key: {}, traceId: {}", chatIndexKey, traceId);
        long chatId = imChat.getChatId();
        String json = ProtobufUtils.toJson(imChat);
        redisTemplate.opsForHash().put(chatIndexKey, String.valueOf(chatId), json);
    }

    public List<ImChat> getAllChat(String userId, String traceId) {
        logger.info("getAllChat userId: {}, traceId: {}", userId, traceId);
        List<ImChat> chatChats = this.getAllChatFromRedis(userId, traceId);
        if (CollectionUtils.isEmpty(chatChats)) {
            logger.info("getAllChat getFromCache is empty traceId: {}", traceId);
            // 从DB查询全量会话
            chatChats = getAlLChatFromDB(userId, traceId);
        }
        if (CollectionUtils.isEmpty(chatChats)) {
            return Collections.emptyList();
        }
        // 获取还存活的会话
        chatChats = getAliveChats(chatChats);
        // 会话最后一条消息
        Map<String, ChatMessage> lastMessagesMap = this.chatMessageComponent.multiLastMessages(userId, chatChats, traceId);
        // 根据会话的最后一条消息补偿会话信息
        chatChats = updateImChat(userId, chatChats, lastMessagesMap, traceId);
        // 会话排序
        sortChats(chatChats);
        return chatChats;
    }

    public List<ImChat> getAllChatFromRedis(String userId, String traceId) {
        String key = RedisKeyUtils.chatIndexKey(userId);
        logger.info("getChatAllFromRedis, key: {}, traceId: {}", key, traceId);
        ScanOptions ops = ScanOptions.scanOptions().count(100).build();
        Map<String, ImChat> imChatMap = new HashMap<>();
        try (Cursor<Map.Entry<String, Object>> scan = redisTemplate.opsForHash().scan(key, ops)) {
            while (scan.hasNext()) {
                Map.Entry<String, Object> next = scan.next();
                String chatId = next.getKey();
                Object value = next.getValue();
                if (value != null) {
                    ImChat imChat = ProtobufUtils.fromJson(String.valueOf(value), ImChat.newBuilder());
                    imChatMap.put(chatId, imChat);
                }
            }
        }
        List<ImChat> imChatDtos = new ArrayList<>(imChatMap.size());
        imChatDtos.addAll(imChatMap.values());
        return imChatDtos;
    }

    private List<ImChat> getAlLChatFromDB(String userId, String traceId) {
        List<ImChat> imChats = this.chatStoreRpc.getAllChat(userId, traceId);
        if (CollectionUtils.isEmpty(imChats)) {
            return Collections.emptyList();
        }
        return imChats;
    }


    private List<ImChat> updateImChat(String userId, List<ImChat> imChatDtos, Map<String, ChatMessage> lastMessagesMap, String traceId) {
        if (CollectionUtils.isEmpty(imChatDtos) || MapUtils.isEmpty(lastMessagesMap)) {
            return new ArrayList<>();
        }
        Set<ImChat> imChats = new HashSet<>();
        Set<ImChat> updated = new HashSet<>();
        for (ImChat imChat : imChatDtos) {
            Long chatId = imChat.getChatId();
            Integer chatType = imChat.getChatType();
            String key = chatMessageComponent.lastMessageKey(userId, String.valueOf(chatId), chatType);
            ChatMessage lastMessage = lastMessagesMap.get(key);
            if (lastMessage == null) {
                imChats.add(imChat);
                continue;
            }
            ImChat.Builder builder = imChat.toBuilder();
            if (this.updateChatDto(builder, lastMessage)) {
                ImChat update = builder.build();
                updated.add(update);
                imChats.add(update);
            } else {
                imChats.add(imChat);
            }
        }
        batchUpdateChat(updated, traceId);
        return new ArrayList<>(imChats);
    }

    /**
     * 批量更新缓存, 批量异步更新DB
     */
    private void batchUpdateChat(Set<ImChat> updated, String traceId) {
        if (CollectionUtils.isEmpty(updated)) {
            return;
        }
        // 更新缓存
        for (ImChat imChat : updated) {
            this.putChat(imChat, traceId);
        }
        // 异步更新数据库
        ThreadPoolUtils.slowPool.execute(() -> {
            this.chatStoreRpc.batchCreateOrUpdate(new ArrayList<>(updated), traceId);
        });
    }

    private List<ImChat> getAliveChats(List<ImChat> chatDtos) {
        List<ImChat> imChatDtos = new ArrayList<>();
        for (ImChat imChatDto : chatDtos) {
            if (imChatDto.getChatDel()) {
                continue;
            }
            imChatDtos.add(imChatDto);
        }
        return imChatDtos;
    }

    private void sortChats(List<ImChat> imChats) {
        if (CollectionUtils.isEmpty(imChats)) {
            return;
        }
        // 按照置顶的会话排序, 然后按照更新时间倒序排序
        imChats.sort((x, y) -> {
            // 相同为0, 不同为1
            if (x.getChatTop() ^ y.getChatTop()) {
                return x.getChatTop() ? -1 : 1;
            }
            // 按照更新时间倒序排序
            return Long.compare(y.getUpdateTimestamp(), x.getUpdateTimestamp());
        });
    }

    /**
     * 更新会话的最后一条消息, 更新会话版本
     */
    private boolean updateChatDto(ImChat.Builder builder, ChatMessage lastMessage) {
        boolean updated = false;
        if (lastMessage == null) {
            return updated;
        }
        long msgId = lastMessage.getMsgId();
        if (Objects.equals(builder.getLastReadMsgId(), msgId)) {
            return updated;
        }
        // 最后一条消息发生变化
        Integer chatType = builder.getChatType();
        if (ChatType.C2G.match(chatType)) {
            //  用户还在群里才更新，不在群里就忽略
            if (!Objects.equals(ChatSubStatus.IN_GROUP.getCode(), builder.getSubStatus())) {
                return updated;
            }
        }
        updated = true;
        builder.setLastReadMsgId(msgId);
        builder.setUpdateTimestamp(lastMessage.getSendTime()); // 会话更新时间
        builder.setChatDel(false); // 激活会话
        return updated;
    }
}
