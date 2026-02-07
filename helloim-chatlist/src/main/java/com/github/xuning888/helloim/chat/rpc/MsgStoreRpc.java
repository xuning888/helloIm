package com.github.xuning888.helloim.chat.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.store.v1.*;
import com.github.xuning888.helloim.contract.contant.CommonConstant;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author xuning
 * @date 2026/2/7 15:39
 */
@Service
public class MsgStoreRpc {

    private static final Logger logger = LoggerFactory.getLogger(MsgStoreRpc.class);

    @DubboReference
    private MsgStoreService msgStoreService;

    public ChatMessage lastMessage(String userId, String chatId, Integer chatType, String traceId) {
        LastMessageRequest request = LastMessageRequest.newBuilder()
                .setUserId(userId)
                .setChatId(chatId)
                .setChatType(chatType)
                .setTraceId(traceId)
                .build();
        try {
            LastMessageResponse response = msgStoreService.lastMessage(request);
            if (response.hasLastMessage()) {
                return response.getLastMessage();
            }
            logger.info("lastMessage result is null, userId: {}, chatId: {}, chatType: {}, traceId: {}",
                    userId, chatId, chatType, traceId);
        } catch (Exception ex) {
            logger.error("lastMessage, errMsg: {}", ex.getMessage(), ex);
        }
        return null;
    }

    public Long maxServerSeq(String from, String to, Integer chatType, String traceId) {
        MaxServerSeqRequest request = MaxServerSeqRequest.newBuilder()
                .setFrom(from).setTo(to).setChatType(chatType).setTraceId(traceId).build();
        try {
            MaxServerSeqResponse response = msgStoreService.maxServerSeq(request);
            return response.getMaxServerSeq();
        } catch (Exception ex) {
            logger.error("maxServerSeq, errorMsg: {}", ex.getMessage(), ex);
            return CommonConstant.ERROR_SERVER_SEQ;
        }
    }
}
