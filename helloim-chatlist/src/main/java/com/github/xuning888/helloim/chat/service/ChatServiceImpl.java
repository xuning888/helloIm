package com.github.xuning888.helloim.chat.service;

import com.github.xuning888.helloim.chat.component.ChatComponent;
import com.github.xuning888.helloim.chat.utils.ServerSeqUtils;
import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.contract.api.service.ChatStoreService;
import com.github.xuning888.helloim.contract.api.service.MsgStoreService;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.convert.ChatConvert;
import com.github.xuning888.helloim.contract.dto.ImChatDto;
import com.github.xuning888.helloim.contract.entity.ImChat;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ChatComponent chatComponent;

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
    public ImChatDto createOrActivateChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        logger.info("createOrActivateChat userId: {}, chatId: {}, chatType: {}, traceId: {}",
                userId, chatId, chatType, traceId);
        // 获取会话缓存
        ImChatDto imChatDto = chatComponent.getChat(String.valueOf(userId), String.valueOf(chatId), traceId);
        if (imChatDto != null) {
            return imChatDto;
        }
        return createChat(userId, chatId, chatType, traceId);
    }

    @Override
    public List<ImChatDto> getALlChat(Long userId, String traceId) {
        return chatComponent.getAllChat(String.valueOf(userId), traceId);
    }

    private ImChatDto createChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        ImChatDto imChatDto = chatComponent.createImChatDto(userId, chatId, chatType.getType(), traceId);
        // 构建会话缓存
        chatComponent.putChat(imChatDto, traceId);
        // 会话存入db
        ImChat imChat = ChatConvert.convertImChat(imChatDto);
        chatStoreService.createOrUpdate(imChat, traceId);
        return imChatDto;
    }

    private Long serverSeqFromDB(String from, String to, ChatType chatType, String traceId) {
        Long serverSeq = null;
        try {
            serverSeq = msgStoreService.maxServerSeq(from, to, chatType.getType(), traceId);
        } catch (Exception ex) {
            logger.error("serverSeqFromDB error. traceId: {}", traceId, ex);
            serverSeq = CommonConstant.ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }
}