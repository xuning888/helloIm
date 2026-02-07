package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.message.v1.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.zookeeper.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 02:08
 */
@DubboService(
        methods = {
                @Method(name = "cleanOfflineMessage", timeout = 10000, retries = 0) // 禁止重试
        }
)
public class MessageServiceImpl extends DubboMessageServiceTriple.MessageServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private OfflineMessageService offlineMessageService;

    @Override
    public OfflineMessageResponse pullOfflineMsg(PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        logger.info("pullOfflineMsg, request: {}, traceId: {}", request, traceId);
        OfflineMessageResponse.Builder responseBuilder = OfflineMessageResponse.newBuilder();
        List<ChatMessage> chatMessages = offlineMessageService.pullOfflineMessage(request, traceId);
        if (CollectionUtils.isEmpty(chatMessages)) {
            logger.debug("pullOfflineMsg chatMessages is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        responseBuilder.addAllOfflineMessages(chatMessages);
        logger.info("pullOfflineMsg, chatMessages.size: {}, traceId: {}", chatMessages.size(), traceId);
        return responseBuilder.build();
    }

    @Override
    public OfflineMessageResponse getLatestOfflineMessages(PullOfflineMsgRequest request) {
        String traceId = request.getTraceId();
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        OfflineMessageResponse.Builder responseBuilder = OfflineMessageResponse.newBuilder();
        List<ChatMessage> messages = offlineMessageService.getLatestOfflineMessages(request, traceId);
        if (CollectionUtils.isEmpty(messages)) {
            logger.debug("getLatestOfflineMessages message is empty, traceId: {}", traceId);
            return responseBuilder.build();
        }
        logger.info("getLatestOfflineMessages message.size: {}, traceId: {}", messages.size(), traceId);
        responseBuilder.addAllOfflineMessages(messages);
        return responseBuilder.build();
    }


    @Override
    public CleanOfflineMsgResponse cleanOfflineMessage(CleanOfflineMsgRequest request) {
        String offlineMsgKey = request.getOfflineMsgKey();
        if (StringUtils.isEmpty(offlineMsgKey)) {
            offlineMessageService.cleanOfflineMessage();
        } else {
            offlineMessageService.doCleanOfflineMessageKey(offlineMsgKey);
        }
        return CleanOfflineMsgResponse.newBuilder().build();
    }
}
