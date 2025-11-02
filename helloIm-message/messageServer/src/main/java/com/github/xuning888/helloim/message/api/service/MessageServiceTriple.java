package com.github.xuning888.helloim.message.api.service;

import com.alibaba.fastjson2.JSON;
import com.github.xuning888.helloim.message.api.DubboMessageServiceTriple;
import com.github.xuning888.helloim.message.api.Msg;
import com.github.xuning888.helloim.message.service.OfflineMessageService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xuning
 * @date 2025/11/2 21:51
 */
@DubboService
public class MessageServiceTriple extends DubboMessageServiceTriple.MessageServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceTriple.class);

    @Resource
    private OfflineMessageService offlineMessageService;

    @Override
    public Msg.ChatMessageList pullOfflineMsg(Msg.PullOfflineMsgRequest request) {
        logger.info("pullOfflineMsg, request: {}", JSON.toJSONString(request));
        String traceId = request.getTraceId();
        List<Msg.ChatMessage> chatMessages = offlineMessageService.pullOfflineMessage(request);
        if (CollectionUtils.isEmpty(chatMessages)) {
            logger.debug("pullOfflineMsg chatMessages is empty, traceId: {}", traceId);
            return Msg.ChatMessageList.newBuilder().addAllMessages(Collections.emptyList()).build();
        }
        return Msg.ChatMessageList.newBuilder().addAllMessages(chatMessages).build();
    }

    @Override
    public Msg.ChatMessageList getLatestOfflineMessages(Msg.PullOfflineMsgRequest request) {
        logger.info("getLatestOfflineMessages, request: {}", JSON.toJSONString(request));
        String traceId = request.getTraceId();
        List<Msg.ChatMessage> messages = offlineMessageService.getLatestOfflineMessages(request);
        if (CollectionUtils.isEmpty(messages)) {
            logger.debug("getLatestOfflineMessages message is empty, traceId: {}", traceId);
            return Msg.ChatMessageList.newBuilder().addAllMessages(Collections.emptyList()).build();
        }
        return Msg.ChatMessageList.newBuilder().addAllMessages(messages).build();
    }
}
