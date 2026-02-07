package com.github.xuning888.helloim.chat.service;

import com.github.xuning888.helloim.api.protobuf.chat.v1.*;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest;
import com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse;
import com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest;
import com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse;
import com.github.xuning888.helloim.chat.component.ChatComponent;
import com.github.xuning888.helloim.chat.component.ChatMessageComponent;
import com.github.xuning888.helloim.chat.rpc.ChatStoreRpc;
import com.github.xuning888.helloim.chat.rpc.MsgStoreRpc;
import com.github.xuning888.helloim.chat.utils.ServerSeqUtils;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.util.RedisKeyUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ChatServiceImpl extends DubboChatServiceTriple.ChatServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ChatStoreRpc chatStoreRpc;

    @Resource
    private MsgStoreRpc msgStoreRpc;

    @Resource
    private ChatComponent chatComponent;

    @Resource
    private ChatMessageComponent chatMessageComponent;

    @Override
    public ServerSeqResponse serverSeq(ServerSeqRequest request) {
        String from = request.getFrom(), to = request.getTo();
        ChatType chatType = ChatType.getChatType(request.getChatType());
        String traceId = request.getTraceId();
        String key = RedisKeyUtils.serverSeqKey(from, to, chatType);
        if (StringUtils.isBlank(key)) {
            logger.error("serverSeq serverSeqKey is null, from: {}, to: {}, chatType: {} traceId: {}", from, to, chatType, traceId);
            return ServerSeqResponse.newBuilder().setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        Long serverSeq = null;
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null) {
            try {
                serverSeq = serverSeqFromDB(from, to, chatType, traceId);
                if (serverSeq == null || serverSeq.equals(CommonConstant.ERROR_SERVER_SEQ)) {
                    logger.error("serverSeq DB query failed, traceId: {}", traceId);
                    return ServerSeqResponse.newBuilder().setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
                }
                serverSeq = ServerSeqUtils.setServerSeq(redisTemplate, key, serverSeq);
                return ServerSeqResponse.newBuilder().setServerSeq(serverSeq).build();
            } catch (Exception ex) {
                logger.error("serverSeq 重建缓存失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, serverSeq, traceId, ex);
                return ServerSeqResponse.newBuilder().setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
            }
        }
        try {
            serverSeq = this.redisTemplate.opsForValue().increment(key);
        } catch (Exception ex) {
            logger.error("serverSeq incr失败, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId, ex);
            return ServerSeqResponse.newBuilder().setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        if (serverSeq == null) {
            logger.error("serverSeq incr失败2, from: {}, to: {}, chatType: {}, traceId: {}", from, to, chatType, traceId);
            return ServerSeqResponse.newBuilder().setServerSeq(CommonConstant.ERROR_SERVER_SEQ).build();
        }
        return ServerSeqResponse.newBuilder().setServerSeq(serverSeq).build();
    }

    @Override
    public CreateOrActivateChatResponse createOrActivateChat(CreateOrActivateChatRequest request) {
        long userId = request.getUserId(), chatId = request.getChatId();
        int chatType = request.getChatType();
        String traceId = request.getTraceId();
        logger.info("createOrActivateChat userId: {}, chatId: {}, chatType: {}, traceId: {}",
                userId, chatId, chatType, traceId);
        ImChat imChat = chatComponent.getChat(String.valueOf(userId), String.valueOf(chatId), traceId);
        if (imChat != null) {
            return CreateOrActivateChatResponse.newBuilder().setImChat(imChat).build();
        }
        imChat = createChat(userId, chatId, ChatType.getChatType(chatType), traceId);
        return CreateOrActivateChatResponse.newBuilder().setImChat(imChat).build();
    }

    @Override
    public GetAllChatResponse getAllChat(GetAllChatRequest request) {
        List<ImChat> allChat = chatComponent.getAllChat(request.getUserId(), request.getTraceId());
        return GetAllChatResponse.newBuilder().addAllChats(allChat).build();
    }

    @Override
    public LastMessageResponse lastMessage(LastMessageRequest request) {
        String userId = request.getUserId(), chatId = request.getChatId(), traceId = request.getTraceId();
        int chatType = request.getChatType();
        ChatMessage lastMessage = chatMessageComponent.getLastMessage(userId, chatId, chatType, traceId);
        return LastMessageResponse.newBuilder().setLastMessage(lastMessage).build();
    }

    @Override
    public GetChatResponse getChat(GetChatRequest request) {
        long userId = request.getUserId(), chatId = request.getChatId();
        String traceId = request.getTraceId();
        ImChat imChat = chatComponent.getChat(String.valueOf(userId), String.valueOf(chatId), traceId);
        GetChatResponse.Builder builder = GetChatResponse.newBuilder();
        if (imChat == null) {
            logger.info("getChat result is null, traceId: {}", traceId);
        } else {
            builder.setImChat(imChat);
        }
        return builder.build();
    }


    private ImChat createChat(Long userId, Long chatId, ChatType chatType, String traceId) {
        ImChat imChat = chatComponent.createImChatDto(userId, chatId, chatType.getType(), traceId);
        // 构建会话缓存
        chatComponent.putChat(imChat, traceId);
        // 会话存入db
        chatStoreRpc.createOrUpdate(imChat, traceId);
        return imChat;
    }

    private Long serverSeqFromDB(String from, String to, ChatType chatType, String traceId) {
        Long serverSeq = null;
        try {
            serverSeq = msgStoreRpc.maxServerSeq(from, to, chatType.getType(), traceId);
        } catch (Exception ex) {
            logger.error("serverSeqFromDB error. traceId: {}", traceId, ex);
            serverSeq = CommonConstant.ERROR_SERVER_SEQ;
        }
        return serverSeq;
    }
}