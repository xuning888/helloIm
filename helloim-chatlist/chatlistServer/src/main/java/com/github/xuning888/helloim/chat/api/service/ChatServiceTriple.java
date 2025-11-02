package com.github.xuning888.helloim.chat.api.service;

import com.github.xuning888.helloim.chat.api.Chat;
import com.github.xuning888.helloim.chat.api.DubboChatServiceTriple;
import com.github.xuning888.helloim.chat.convert.ChatConvert;
import com.github.xuning888.helloim.chat.rpc.ChatStoreServiceRpc;
import com.github.xuning888.helloim.chat.rpc.MsgStoreServiceRpc;
import com.github.xuning888.helloim.chat.utils.ChatUtils;
import com.github.xuning888.helloim.chat.utils.ServerSeqUtils;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import com.github.xuning888.helloim.store.api.Store;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/11/2 15:51
 */
@DubboService
public class ChatServiceTriple extends DubboChatServiceTriple.ChatServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceTriple.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ChatStoreServiceRpc chatStoreServiceRpc;

    @Resource
    private MsgStoreServiceRpc msgStoreServiceRpc;


    @Override
    public Chat.ServerSeqResponse serverSeq(Chat.ServerSeqRequest request) {
        String from = request.getFrom(), to = request.getTo(), traceId = request.getTraceId();
        ChatType chatType = ChatType.convert(request.getChatType());
        Chat.ServerSeqResponse.Builder responseBuilder = Chat.ServerSeqResponse.newBuilder();
        if (Objects.isNull(chatType)) {
            logger.error("serverSeq unknown chatType, request: {}, traceId: {}", request, traceId);
            return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        String key = RedisKeyUtils.serverSeqKey(from, to, chatType);
        if (StringUtils.isBlank(key)) {
            logger.error("serverSeq serverSeqKey is null, request: {}, traceId: {}", request, traceId);
            return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        Long serverSeq = null;
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            try {
                serverSeq = serverSeqFromDB(from, to, chatType, traceId);
                if (serverSeq == null || serverSeq.equals(CommonConstant.ERROR_SERVER_SEQ)) {
                    logger.error("serverSeq DB query failed, traceId: {}", traceId);
                    return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
                }
                serverSeq = ServerSeqUtils.setServerSeq(redisTemplate, key, serverSeq);
                return responseBuilder.setServerSeq(serverSeq).build();
            } catch (Exception ex) {
                logger.error("serverSeq 重建缓存失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, serverSeq, traceId, ex);
                return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
            }
        }
        try {
            serverSeq = this.redisTemplate.opsForValue().increment(key);
        } catch (Exception ex) {
            logger.error("serverSeq incr失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId, ex);
            return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        if (serverSeq == null) {
            logger.error("serverSeq incr失败2, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return responseBuilder.setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        return responseBuilder.setServerSeq(serverSeq).build();
    }

    @Override
    public Chat.ImChat createOrActivateChat(Chat.CreateOrActivateChatRequest request) {
        long userId = request.getUserId(), chatId = request.getChatId();
        int chatType = request.getChatType();
        String traceId = request.getTraceId();
        logger.info("createOrActivateChat userId: {}, chatId: {}, chatType: {}, traceId: {}",
                userId, chatId, chatType, traceId);

        ChatType convert = ChatType.convert(chatType);
        if (convert == null) {
            logger.error("createOrActivateChat unknown chatType: {}, traceId: {}", chatType, traceId);
            throw new IllegalArgumentException("unKnown chatType: " + chatType);
        }
        // 获取会话缓存
        Chat.ImChat imChat = ChatUtils.getChat(this.redisTemplate, String.valueOf(userId), String.valueOf(chatId), traceId);
        if (imChat == null) {
            imChat = createChat(userId, chatId, convert, traceId);
        } else {
            imChat = imChat.toBuilder()
                    .setUpdateTimestamp(System.currentTimeMillis())
                    .setChatDel(false)
                    .build();
            updateChat(imChat, traceId);
        }
        return imChat;
    }


    private Chat.ImChat createChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        Store.ImChat.Builder builder = Store.ImChat.newBuilder();
        builder.setUserId(userId);
        builder.setChatId(chatId);
        builder.setChatType(chatType.getType());
        builder.setChatDel(0); // 是否逻辑删除
        builder.setChatTop(0); // 是否置顶
        builder.setChatDel(0); // 是否逻辑删除
        builder.setChatMute(0); // 是否静默会话
        builder.setUpdateTimestamp(System.currentTimeMillis());
        builder.setDelTimestamp(0);
        Store.ImChat imChat = builder.build();
        Store.CreateOrUpdateChatRequest request = Store.CreateOrUpdateChatRequest.newBuilder()
                .setImChat(imChat).setTraceId(traceId).build();
        Store.CreateOrUpdateChatResponse response = chatStoreServiceRpc.createOrUpdate(request);
        int raw = response.getRaw();
        Chat.ImChat chatChat = ChatConvert.convert2ChatChat(imChat);
        if (raw > 0) {
            // 重建会话缓存
            ChatUtils.putChat(this.redisTemplate, chatChat, traceId);
        }
        return chatChat;
    }

    private void updateChat(Chat.ImChat imChat, String traceId) {
        Store.ImChat storeChat = ChatConvert.convert2StoreChat(imChat);
        Store.CreateOrUpdateChatRequest.Builder builder = Store.CreateOrUpdateChatRequest.newBuilder();
        Store.CreateOrUpdateChatRequest request = builder.setImChat(storeChat).setTraceId(traceId).build();
        Store.CreateOrUpdateChatResponse response = chatStoreServiceRpc.createOrUpdate(request);
        int raw = response.getRaw();
        if (raw > 0) {
            ChatUtils.putChat(this.redisTemplate, imChat, traceId);
        }
    }

    private Long serverSeqFromDB(String from, String to, ChatType chatType, String traceId) {
        Store.MaxServerSeqRequest request = Store.MaxServerSeqRequest.newBuilder().setFrom(from).setTo(to)
                .setChatType(chatType.getType()).setTraceId(traceId).build();
        Store.MaxServerSeqResponse response = msgStoreServiceRpc.maxServerSeq(request);
        long serverSeq = response.getServerSeq();
        if (Objects.equals(serverSeq, CommonConstant.ERROR_SERVER_SEQ)) {
            logger.error("serverSeqFromDB error from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }
}