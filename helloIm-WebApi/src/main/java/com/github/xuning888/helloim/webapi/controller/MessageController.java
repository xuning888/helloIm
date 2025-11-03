package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.contant.RestResultStatus;
import com.github.xuning888.helloim.contract.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import com.github.xuning888.helloim.message.api.Msg;
import com.github.xuning888.helloim.webapi.convert.WebApiConvert;
import com.github.xuning888.helloim.webapi.dto.ChatMessage;
import com.github.xuning888.helloim.webapi.rpc.MessageServiceRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuning
 * @date 2025/10/7 02:02
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageServiceRpc messageServiceRpc;

    @GetMapping("/pullOfflineMsg")
    public RestResult<Object> pullOfflineMsg(@RequestParam("fromUserId") String fromUserId,
                                      @RequestParam("chatId") String chatId,
                                      @RequestParam("chatType") Integer chatType,
                                      @RequestParam("minServerSeq") Long minServerSeq,
                                      @RequestParam("maxServerSeq") Long maxServerSeq) {
        String traceId = UUID.randomUUID().toString();
        logger.info("/message/pullOfflineMsg fromUserId: {}, chatId: {}, chatType: {}, minServerSeq: {}, maxServerSeq: {}, traceId: {}",
                fromUserId, chatId, chatType, minServerSeq, maxServerSeq, traceId);
        Msg.PullOfflineMsgRequest.Builder builder = Msg.PullOfflineMsgRequest.newBuilder();
        builder.setFromUserId(Long.parseLong(fromUserId));
        builder.setChatId(Long.parseLong(chatId));
        builder.setChatType(chatType);
        builder.setMinServerSeq(minServerSeq);
        builder.setMaxServerSeq(maxServerSeq);
        builder.setSize(0);
        builder.setTraceId(traceId);
        Msg.PullOfflineMsgRequest request = builder.build();
        try {
            Msg.ChatMessageList response = messageServiceRpc.pullOfflineMsg(request);
            List<ChatMessage> messages = response.getMessagesList().stream().map(WebApiConvert::chatMessageFromProtobuf).collect(Collectors.toList());
            return RestResultUtils.success(messages);
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
        logger.info("/message/getLatestOfflineMessages fromUserId: {}, chatId: {}, chatType: {}, size: {}, traceId: {}",
                fromUserId, chatId, chatType, size, traceId);
        Msg.PullOfflineMsgRequest.Builder builder = Msg.PullOfflineMsgRequest.newBuilder();
        builder.setFromUserId(Long.parseLong(fromUserId));
        builder.setChatId(Long.parseLong(chatId));
        builder.setChatType(chatType);
        builder.setMinServerSeq(0);
        builder.setMaxServerSeq(0);
        builder.setSize(size);
        builder.setTraceId(traceId);
        Msg.PullOfflineMsgRequest request = builder.build();
        try {
            Msg.ChatMessageList response = messageServiceRpc.getLatestOfflineMessages(request);
            List<ChatMessage> messages = response.getMessagesList().stream().map(WebApiConvert::chatMessageFromProtobuf).collect(Collectors.toList());
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
