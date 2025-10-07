package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.api.service.MessageService;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author xuning
 * @date 2025/10/7 02:02
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @DubboReference
    private MessageService messageService;

    @GetMapping("/pullOfflineMsg")
    public RestResult<Object> pullMsg(@RequestParam("fromUserId") String fromUserId,
                                      @RequestParam("chatId") String chatId,
                                      @RequestParam("chatType") Integer chatType,
                                      @RequestParam("minServerSeq") Long minServerSeq,
                                      @RequestParam("maxServerSeq") Long maxServerSeq) {
        PullOfflineMsgRequest pullOfflineMsgRequest = new PullOfflineMsgRequest();
        pullOfflineMsgRequest.setFromUserId(Long.valueOf(fromUserId));
        pullOfflineMsgRequest.setChatId(Long.valueOf(chatId));
        pullOfflineMsgRequest.setChatType(chatType);
        pullOfflineMsgRequest.setMinServerSeq(minServerSeq);
        pullOfflineMsgRequest.setMaxServerSeq(maxServerSeq);
        List<ChatMessage> chatMessages = messageService.pullOfflineMsg(pullOfflineMsgRequest, UUID.randomUUID().toString());
        return RestResultUtils.success(chatMessages);
    }


    @GetMapping("/getLatestOfflineMessages")
    public RestResult<Object> getLatestOfflineMessages(@RequestParam String fromUserId,
                                                       @RequestParam String chatId,
                                                       @RequestParam Integer chatType,
                                                       @RequestParam Integer size) {
        PullOfflineMsgRequest request = new PullOfflineMsgRequest();
        request.setFromUserId(Long.parseLong(fromUserId));
        request.setChatId(Long.parseLong(chatId));
        request.setChatType(chatType);
        request.setSize(size);
        List<ChatMessage> messages = messageService.getLatestOfflineMessages(request, UUID.randomUUID().toString());
        return RestResultUtils.success(messages);
    }
}
