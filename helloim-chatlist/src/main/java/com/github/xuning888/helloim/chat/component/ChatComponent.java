package com.github.xuning888.helloim.chat.component;

import com.github.xuning888.helloim.chat.utils.ThreadPoolUtils;
import com.github.xuning888.helloim.contract.api.service.ChatStoreService;
import com.github.xuning888.helloim.contract.api.service.UserGroupService;
import com.github.xuning888.helloim.contract.contant.ChatSubStatus;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.convert.ChatConvert;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.ImChatDto;
import com.github.xuning888.helloim.contract.entity.ImChat;
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

    @DubboReference
    private ChatStoreService chatStoreService;

    @DubboReference
    private UserGroupService userGroupService;

    @Resource
    private ChatMessageComponent chatMessageComponent;

    public ImChatDto createImChatDto(Long userId, Long chatId, Integer chatType, String traceId) {
        ImChatDto imChatDto = new ImChatDto();
        imChatDto.setUserId(userId);
        imChatDto.setChatId(chatId);
        imChatDto.setChatType(chatType);
        imChatDto.setChatTop(false);
        imChatDto.setChatMute(false);
        imChatDto.setChatDel(false);
        imChatDto.setUpdateTimestamp(System.currentTimeMillis());
        imChatDto.setDelTimestamp(System.currentTimeMillis());
        ChatMessageDto lastMessage = chatMessageComponent.getLastMessage(userId.toString(), chatId.toString(), chatType, traceId);
        if (lastMessage != null) {
            updateChatDto(imChatDto, lastMessage);
        }
        if (ChatType.C2G.match(chatType)) {
            Date userJoinGroupTime = userGroupService.getUserJoinGroupTime(chatId, String.valueOf(userId), traceId);
            Long updateTimestamp = null;
            if (userJoinGroupTime == null) {
                updateTimestamp = System.currentTimeMillis();
            } else {
                updateTimestamp = userJoinGroupTime.getTime();
            }
            imChatDto.setUpdateTimestamp(updateTimestamp);
            imChatDto.setSubStatus(ChatSubStatus.IN_GROUP.getCode());
        }
        return imChatDto;
    }


    public ImChatDto getChat(String userId, String chatId, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(userId);
        Object value = redisTemplate.opsForHash().get(chatIndexKey, chatId);
        if (value == null) {
            logger.warn("getChat is null, userId: {}, chatId: {}, traceId: {}", userId, chatId, traceId);
            return null;
        }
        ImChatDto imchatDto = (ImChatDto) value;
        ChatMessageDto lastMessage = this.chatMessageComponent.getLastMessage(userId, chatId, imchatDto.getChatType(), traceId);
        if (lastMessage != null) {
            if (updateChatDto(imchatDto, lastMessage)) {
                batchUpdateChat(Collections.singleton(imchatDto), traceId);
            }
        }
        return imchatDto;
    }

    public void putChat(ImChatDto imChat, String traceId) {
        String chatIndexKey = RedisKeyUtils.chatIndexKey(String.valueOf(imChat.getUserId()));
        logger.info("putChat key: {}, traceId: {}", chatIndexKey, traceId);
        redisTemplate.opsForHash().put(chatIndexKey, imChat.getChatId(), imChat);
    }

    public List<ImChatDto> getAllChat(String userId, String traceId) {
        List<ImChatDto> imChatDtos = this.getAllChatFromRedis(userId, traceId);
        if (CollectionUtils.isEmpty(imChatDtos)) {
            // 从DB查询全量会话
            imChatDtos = getAlLChatFromDB(userId, traceId);
        }
        if (CollectionUtils.isEmpty(imChatDtos)) {
            return Collections.emptyList();
        }
        // 会话最后一条消息
        Map<String, ChatMessageDto> lastMessagesMap = this.chatMessageComponent.multiLastMessages(userId, imChatDtos, traceId);
        // 根据会话的最后一条消息补偿会话信息
        updateImChatDto(imChatDtos, lastMessagesMap, traceId);
        // 获取还存活的会话
        List<ImChatDto> aliveChats = getAliveChats(imChatDtos);
        // 会话排序
        sortChats(aliveChats);
        return aliveChats;
    }

    public List<ImChatDto> getAllChatFromRedis(String userId, String traceId) {
        String key = RedisKeyUtils.chatIndexKey(userId);
        logger.info("getChatAllFromRedis, key: {}, traceId: {}", key, traceId);
        ScanOptions ops = ScanOptions.scanOptions().count(100).build();
        Map<String, ImChatDto> imChatDtoMap = new HashMap<>();
        try (Cursor<Map.Entry<String, ImChatDto>> scan = redisTemplate.opsForHash().scan(key, ops)) {
            while (scan.hasNext()) {
                Map.Entry<String, ImChatDto> next = scan.next();
                String chatId = next.getKey();
                ImChatDto value = next.getValue();
                imChatDtoMap.put(chatId, value);
            }
        }
        List<ImChatDto> imChatDtos = new ArrayList<>(imChatDtoMap.size());
        imChatDtos.addAll(imChatDtoMap.values());
        return imChatDtos;
    }


    private List<ImChatDto> getAlLChatFromDB(String userId, String traceId) {
        List<ImChat> imChats = this.chatStoreService.getAllChat(userId, traceId);
        if (CollectionUtils.isEmpty(imChats)) {
            return Collections.emptyList();
        }
        List<ImChatDto> imChatDtos = new ArrayList<>();
        for (ImChat imChat : imChats) {
            ImChatDto imChatDto = ChatConvert.convertToimChatDto(imChat);
            imChatDtos.add(imChatDto);
        }
        return imChatDtos;
    }


    private void updateImChatDto(List<ImChatDto> imChatDtos, Map<String, ChatMessageDto> lastMessagesMap, String traceId) {
        if (CollectionUtils.isEmpty(imChatDtos) || MapUtils.isEmpty(lastMessagesMap)) {
            return;
        }
        Set<ImChatDto> updated = new HashSet<>();
        for (ImChatDto imChatDto : imChatDtos) {
            Long chatId = imChatDto.getChatId();
            ChatMessageDto lastMessage = lastMessagesMap.get(String.valueOf(chatId));
            if (lastMessage == null) {
                continue;
            }
            if (this.updateChatDto(imChatDto, lastMessage)) {
                updated.add(imChatDto);
            }
            updated.add(imChatDto);
        }
        batchUpdateChat(updated, traceId);
    }

    private void batchUpdateChat(Set<ImChatDto> updated, String traceId) {
        if (CollectionUtils.isEmpty(updated)) {
            return;
        }
        // 更新缓存
        List<ImChat> imChats = new ArrayList<>();
        for (ImChatDto imChatDto : updated) {
            this.putChat(imChatDto, traceId);
            imChats.add(ChatConvert.convertImChat(imChatDto));
        }
        // 异步更新数据库
        ThreadPoolUtils.slowPool.execute(() -> {
            this.chatStoreService.batchCreateOrUpdate(imChats, traceId);
        });
    }

    private List<ImChatDto> getAliveChats(List<ImChatDto> chatDtos) {
        List<ImChatDto> imChatDtos = new ArrayList<>();
        for (ImChatDto imChatDto : chatDtos) {
            if (imChatDto.getChatDel()) {
                continue;
            }
            imChatDtos.add(imChatDto);
        }
        return imChatDtos;
    }

    private void sortChats(List<ImChatDto> imChatDtos) {
        if (CollectionUtils.isEmpty(imChatDtos)) {
            return;
        }
        // 按照置顶的会话排序, 然后按照更新时间倒序排序
        imChatDtos.sort((x, y) -> {
            // 相同为0, 不同为1
            if (x.getChatTop() ^ y.getChatTop()) {
                return x.getChatTop() ? -1 : 1;
            }
            // 按照更新时间倒序排序
            return Long.compare(y.getUpdateTimestamp(), x.getUpdateTimestamp());
        });
    }

    private boolean updateChatDto(ImChatDto imChatDto, ChatMessageDto lastMessage) {
        boolean updated = false;
        if (imChatDto == null || lastMessage == null) {
            return updated;
        }
        Long msgId = lastMessage.getMsgId();
        if (Objects.equals(imChatDto.getLastReadMsgId(), msgId)) {
            return updated;
        }
        // 最后一条消息发生变化
        Integer chatType = imChatDto.getChatType();
        if (ChatType.C2G.match(chatType)) {
            //  用户还在群里才更新，不在群里就忽略
            if (!Objects.equals(ChatSubStatus.IN_GROUP.getCode(), imChatDto.getSubStatus())) {
                return updated;
            }
        }
        updated = true;
        imChatDto.setLastReadMsgId(msgId);
        imChatDto.setUpdateTimestamp(lastMessage.getSendTime()); // 会话更新时间
        imChatDto.setChatDel(false); // 激活会话
        return updated;
    }
}
