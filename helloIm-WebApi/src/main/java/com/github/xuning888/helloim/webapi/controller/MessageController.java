package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.api.request.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.api.service.MessageService;
import com.github.xuning888.helloim.contract.contant.RestResultStatus;
import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @DubboReference
    private MessageService messageService;

    @GetMapping("/pullOfflineMsg")
    public RestResult<Object> pullOfflineMsg(@RequestParam("fromUserId") String fromUserId,
                                      @RequestParam("chatId") String chatId,
                                      @RequestParam("chatType") Integer chatType,
                                      @RequestParam("minServerSeq") Long minServerSeq,
                                      @RequestParam("maxServerSeq") Long maxServerSeq) {
        String traceId = UUID.randomUUID().toString();
        PullOfflineMsgRequest pullOfflineMsgRequest = new PullOfflineMsgRequest();
        pullOfflineMsgRequest.setFromUserId(Long.valueOf(fromUserId));
        pullOfflineMsgRequest.setChatId(Long.valueOf(chatId));
        pullOfflineMsgRequest.setChatType(chatType);
        pullOfflineMsgRequest.setMinServerSeq(minServerSeq);
        pullOfflineMsgRequest.setMaxServerSeq(maxServerSeq);
        logger.info("/message/pullOfflineMsg request: {}, traceId: {}", pullOfflineMsgRequest, traceId);
        try {
            List<ChatMessage> chatMessages = messageService.pullOfflineMsg(pullOfflineMsgRequest, traceId);
            return RestResultUtils.success(chatMessages);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("pullOfflineMsg illegalArgumentException traceId: {}", traceId, illegalArgumentException);
            return RestResultUtils.withStatus(RestResultStatus.INVALID);
        } catch (Exception ex) {
            logger.error("pullOfflineMsg unknown error, traceId: {}", traceId, ex);
            return RestResultUtils.withStatus(RestResultStatus.SERVER_ERR);
        }
    }


    @GetMapping("/getLatestOfflineMessages")
    public RestResult<Object> getLatestOfflineMessages(@RequestParam("fromUserId") String fromUserId,
                                                       @RequestParam("chatId") String chatId,
                                                       @RequestParam("chatType") Integer chatType,
                                                       @RequestParam(value = "size", defaultValue = "50", required = false) Integer size) {
        String traceId = UUID.randomUUID().toString();
        PullOfflineMsgRequest request = new PullOfflineMsgRequest();
        request.setFromUserId(Long.parseLong(fromUserId));
        request.setChatId(Long.parseLong(chatId));
        request.setChatType(chatType);
        request.setSize(size);
        logger.info("/message/getLatestOfflineMessages request: {}, traceId: {}", request, traceId);
        try {
            List<ChatMessage> messages = messageService.getLatestOfflineMessages(request, UUID.randomUUID().toString());
            return RestResultUtils.success(messages);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("getLatestOfflineMessages illegalArgumentException traceId: {}", traceId, illegalArgumentException);
            return RestResultUtils.withStatus(RestResultStatus.INVALID);
        } catch (Exception ex) {
            logger.error("getLatestOfflineMessages unknown error, traceId: {}", traceId, ex);
            return RestResultUtils.withStatus(RestResultStatus.SERVER_ERR);
        }
    }
}
