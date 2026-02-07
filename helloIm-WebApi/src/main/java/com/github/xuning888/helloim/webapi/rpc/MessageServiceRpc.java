package com.github.xuning888.helloim.webapi.rpc;


import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.message.v1.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 16:16
 */
@Service
public class MessageServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceRpc.class);

    @DubboReference
    private MessageService messageService;

    public List<ChatMessage> pullOfflineMsg(PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        try {
            OfflineMessageResponse response = messageService.pullOfflineMsg(request);
            return response.getOfflineMessagesList();
        } catch (Exception ex) {
            logger.error("pullOfflineMsg errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
        }
        return Collections.emptyList();
    }

    public List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        try {
            OfflineMessageResponse response = messageService.getLatestOfflineMessages(request);
            return response.getOfflineMessagesList();
        } catch (Exception ex) {
            logger.error("pullOfflineMsg errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
        }
        return Collections.emptyList();
    }

    public void cleanOfflineMessage(String offlineMessageKey, String traceId) {
        CleanOfflineMsgRequest.Builder builder = CleanOfflineMsgRequest.newBuilder();
        builder.setOfflineMsgKey(offlineMessageKey);
        builder.setTraceId(traceId);
        CleanOfflineMsgRequest request = builder.build();
        try {
            CleanOfflineMsgResponse cleanOfflineMsgResponse = messageService.cleanOfflineMessage(request);
            logger.info("cleanOfflineMessage, response: {}", cleanOfflineMsgResponse);
        } catch (Exception ex) {
            logger.error("cleanOfflineMessage, errMsg: {}, traceId: {}", ex.getMessage(), traceId, ex);
        }
    }
}
