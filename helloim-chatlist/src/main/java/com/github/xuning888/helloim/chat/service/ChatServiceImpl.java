package com.github.xuning888.helloim.chat.service;

import com.github.xuning888.helloim.chat.utils.ChatIndexUtils;
import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import com.github.xuning888.helloim.chat.utils.ServerSeqUtils;
import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.api.service.ChatStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.entity.ImChat;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 19:37
 */
@DubboService
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Resource
    private RedisTemplate redisTemplate;

    @DubboReference
    private ChatStoreService chatStoreService;

    @DubboReference
    private MsgStoreService msgStoreService;

    @Override
    public Long serverSeq(String from, String to, ChatType chatType, String traceId) {
        String key = RedisKeyUtils.serverSeqKey(from, to, chatType);
        if (StringUtils.isBlank(key)) {
            logger.error("serverSeq serverSeqKey is null, from: {}, to: {}, chatType: {} traceId: {}", from, to, chatType, traceId);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
        Long serverSeq = null;
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            try {
                serverSeq = serverSeqFromDB(from, to, chatType, traceId);
                if (serverSeq == null || serverSeq.equals(CommonConstant.ERROR_SERVER_SEQ)) {
                    logger.error("serverSeq DB query failed, traceId: {}", traceId);
                    return CommonConstant.ERROR_SERVER_SEQ;
                }
                return ServerSeqUtils.setServerSeq(redisTemplate, key, serverSeq);
            } catch (Exception ex) {
                logger.error("serverSeq 重建缓存失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, serverSeq, traceId, ex);
                return CommonConstant.ERROR_SERVER_SEQ;
            }
        }
        try {
            serverSeq = this.redisTemplate.opsForValue().increment(key);
        } catch (Exception ex) {
            logger.error("serverSeq incr失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId, ex);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
        if (serverSeq == null) {
            logger.error("serverSeq incr失败2, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }

    @Override
    public ImChat createOrActivateChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        logger.info("createOrActivateChat userId: {}, chatId: {}, chatType: {}, traceId: {}",
                userId, chatId, chatType, traceId);
        // 获取会话缓存
        ImChat imChat = ChatIndexUtils.getChat(this.redisTemplate, String.valueOf(userId), String.valueOf(chatId), traceId);
        if (imChat == null) {
            imChat = createChat(userId, chatId, chatType, traceId);
        } else {
            imChat.setUpdateTimestamp(new Date());
            imChat.setChatDel(0); // 是否逻辑删除
            updateChat(imChat, traceId);
        }
        return imChat;
    }

    private ImChat createChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        ImChat imChat = new ImChat();
        imChat.setUserId(userId);
        imChat.setChatId(chatId);
        imChat.setChatType(chatType.getType());
        imChat.setChatDel(0); // 是否逻辑删除
        imChat.setChatTop(0);
        imChat.setChatDel(0);
        imChat.setChatMute(0);
        imChat.setUpdateTimestamp(new Date());
        imChat.setDelTimestamp(new Date());
        int raw = chatStoreService.createOrUpdate(imChat, traceId);
        if (raw > 0) {
            // 重建会话缓存
            ChatIndexUtils.putChat(this.redisTemplate, imChat, traceId);
        }
        return imChat;
    }

    private void updateChat(ImChat imChat, String traceId) {
        int raw = this.chatStoreService.createOrUpdate(imChat, traceId);
        if (raw > 0) {
            ChatIndexUtils.putChat(this.redisTemplate, imChat, traceId);
        }
    }

    private Long serverSeqFromDB(String from, String to, ChatType chatType, String traceId) {
        Long serverSeq = msgStoreService.maxServerSeq(from, to, chatType.getType(), traceId);
        if (Objects.equals(serverSeq, CommonConstant.ERROR_SERVER_SEQ)) {
            logger.error("serverSeqFromDB error from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }
}