package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.api.convert.MessageConvert;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest;
import com.github.xuning888.helloim.contract.contant.RestResultStatus;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import com.github.xuning888.helloim.api.dto.RestResult;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
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
        PullOfflineMsgRequest.Builder builder = PullOfflineMsgRequest.newBuilder();
        builder.setFromUserId(Long.parseLong(fromUserId));
        builder.setChatId(Long.parseLong(chatId));
        builder.setChatType(chatType);
        builder.setMinServerSeq(minServerSeq);
        builder.setMaxServerSeq(maxServerSeq);
        builder.setTraceId(traceId);
        PullOfflineMsgRequest request = builder.build();
        logger.info("/message/pullOfflineMsg request: {}, traceId: {}", request, traceId);
        try {
            List<ChatMessage> chatMessages = messageServiceRpc.pullOfflineMsg(request);
            List<ChatMessageDto> messageDtos = MessageConvert.pbConvert2Dto(chatMessages);
            return RestResultUtils.success(messageDtos);
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
        PullOfflineMsgRequest.Builder builder = PullOfflineMsgRequest.newBuilder();
        builder.setFromUserId(Long.parseLong(fromUserId));
        builder.setChatId(Long.parseLong(chatId));
        builder.setChatType(chatType);
        builder.setSize(size);
        builder.setTraceId(traceId);
        PullOfflineMsgRequest request = builder.build();
        logger.info("/message/getLatestOfflineMessages request: {}, traceId: {}", request, traceId);
        try {
            List<ChatMessage> messages = messageServiceRpc.getLatestOfflineMessages(request);
            List<ChatMessageDto> messageDtos = MessageConvert.pbConvert2Dto(messages);
            return RestResultUtils.success(messageDtos);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("getLatestOfflineMessages illegalArgumentException traceId: {}", traceId, illegalArgumentException);
            return RestResultUtils.withStatus(RestResultStatus.INVALID);
        } catch (Exception ex) {
            logger.error("getLatestOfflineMessages unknown error, traceId: {}", traceId, ex);
            return RestResultUtils.withStatus(RestResultStatus.SERVER_ERR);
        }
    }

    @GetMapping("/cleanOfflineMessage")
    public RestResult<Object> cleanOfflineMessage(@RequestParam(value = "offlineMsgKey", required = false) String offlineMsgKey) {
        String traceId = UUID.randomUUID().toString();
        try {
            messageServiceRpc.cleanOfflineMessage(offlineMsgKey, traceId);
            return RestResultUtils.success();
        } catch (Exception ex) {
            logger.error("getLatestOfflineMessages unknown error", ex);
            return RestResultUtils.withStatus(RestResultStatus.SERVER_ERR);
        }
    }
}
