package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 02:05
 */
public interface MessageService {

    List<ChatMessageDto> pullOfflineMsg(PullOfflineMsgRequest request, String traceId);


    List<ChatMessageDto> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId);

    void cleanOfflineMessage(String offlineMessageKey);
}
