package com.github.xuning888.helloim.message.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.api.service.MessageService;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 02:08
 */
@DubboService
public class MessageServiceImpl implements MessageService {

    @Autowired
    private OfflineMessageService offlineMessageService;

    @Override
    public List<ChatMessage> pullOfflineMsg(PullOfflineMsgRequest request, String traceId) {
        return offlineMessageService.pullOfflineMessage(request, traceId);
    }

    @Override
    public List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId) {
        return offlineMessageService.getLatestOfflineMessages(request, traceId);
    }
}
