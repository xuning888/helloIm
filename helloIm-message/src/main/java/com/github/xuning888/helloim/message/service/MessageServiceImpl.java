package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.api.service.MessageService;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 02:08
 */
@DubboService
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private OfflineMessageService offlineMessageService;

    @Override
    public List<ChatMessage> pullOfflineMsg(PullOfflineMsgRequest request, String traceId) {
        logger.info("pullOfflineMsg, request: {}, traceId: {}", request, traceId);
        List<ChatMessage> chatMessages = offlineMessageService.pullOfflineMessage(request, traceId);
        if (CollectionUtils.isEmpty(chatMessages)) {
            logger.debug("pullOfflineMsg chatMessages is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        logger.info("pullOfflineMsg, chatMessages.size: {}, traceId: {}", chatMessages.size(), traceId);
        return chatMessages;
    }

    @Override
    public List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
        logger.info("getLatestOfflineMessages, request: {}, traceId: {}", request, traceId);
        List<ChatMessage> messages = offlineMessageService.getLatestOfflineMessages(request, traceId);
        if (CollectionUtils.isEmpty(messages)) {
            logger.debug("getLatestOfflineMessages message is empty, traceId: {}", traceId);
            return Collections.emptyList();
        }
        logger.info("getLatestOfflineMessages message.size: {}, traceId: {}", messages.size(), traceId);
        return messages;
    }
}
