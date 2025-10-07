package com.github.xuning888.helloim.contract.api.service;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.dto.ChatMessage;

import java.util.List;

/**
 * @author xuning
 * @date 2025/10/7 02:05
 */
public interface MessageService {

    List<ChatMessage> pullOfflineMsg(PullOfflineMsgRequest request, String traceId);


    List<ChatMessage> getLatestOfflineMessages(PullOfflineMsgRequest request, String traceId);

}
