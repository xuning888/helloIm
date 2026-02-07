package com.github.xuning888.helloim.chat.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.api.protobuf.store.v1.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 18:03
 */
@Service
public class ChatStoreRpc {

    private static final Logger logger = LoggerFactory.getLogger(ChatStoreRpc.class);

    @DubboReference
    private ChatStoreService chatStoreService;

    public int createOrUpdate(ImChat imChat, String traceId) {
        CreateOrUpdateChatRequest request = CreateOrUpdateChatRequest.newBuilder().setImChat(imChat).setTraceId(traceId).build();
        try {
            CreateOrUpdateChatResponse response = chatStoreService.createOrUpdate(request);
            return response.getResult();
        } catch (Exception ex) {
            logger.error("createOUpdate, errMsg: {}, traceId: {}",ex.getMessage(), traceId, ex);
            return 0;
        }
    }

    public List<ImChat> getAllChat(String userId, String traceId) {
        GetAllChatRequest request = GetAllChatRequest.newBuilder().setUserId(userId).setTraceId(traceId).build();
        try {
            GetAllChatResponse response = chatStoreService.getAllChat(request);
            return response.getChatsList();
        } catch (Exception ex) {
            logger.error("createOUpdate, errMsg: {}, traceId: {}",ex.getMessage(), traceId, ex);
            return Collections.emptyList();
        }
    }

    public int batchCreateOrUpdate(List<ImChat> imChats, String traceId) {
        BatchCreateOrUpdateRequest request = BatchCreateOrUpdateRequest.newBuilder().addAllChats(imChats).setTraceId(traceId).build();
        try {
            BatchCreateOrUpdateResponse response = chatStoreService.batchCreateOrUpdate(request);
            return response.getResult();
        } catch (Exception ex) {
            logger.error("batchCreateOrUpdate, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
            return 0;
        }
    }
}
