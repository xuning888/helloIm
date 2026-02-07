package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.api.convert.ImChatConvert;
import com.github.xuning888.helloim.api.convert.MessageConvert;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import com.github.xuning888.helloim.api.dto.ImChatDto;
import com.github.xuning888.helloim.api.dto.RestResult;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImChat;
import com.github.xuning888.helloim.contract.util.RestResultUtils;
import com.github.xuning888.helloim.webapi.rpc.ChatServiceRpc;
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
 * @date 2025/11/8 23:18
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Resource
    private ChatServiceRpc chatServiceRpc;

    @GetMapping("/getAllChat")
    public RestResult<Object> getAllChat(@RequestParam("userId") String userId) {
        String traceId = UUID.randomUUID().toString();
        logger.info("/chat/getAllChat, userId: {}, traceId: {}", userId, traceId);
        List<ImChat> aLlChat = this.chatServiceRpc.getALlChat(Long.parseLong(userId), traceId);
        List<ImChatDto> imChatDtos = ImChatConvert.convert2Dto(aLlChat);
        return RestResultUtils.success(imChatDtos);
    }

    @GetMapping("/getChat")
    public RestResult<Object> getChat(@RequestParam("userId") String userId,
                                      @RequestParam("chatId") String chatId) {
        String traceId = UUID.randomUUID().toString();
        logger.info("/chat/getChat, userId: {}, chatId: {} traceId: {}", userId, chatId, traceId);
        ImChat imChat = this.chatServiceRpc.getChat(Long.parseLong(userId), Long.parseLong(chatId), traceId);
        if (imChat == null) {
            return RestResultUtils.success();
        }
        ImChatDto imChatDo = ImChatConvert.convert2Dto(imChat);
        return RestResultUtils.success(imChatDo);
    }

    @GetMapping("/lastMessage")
    public RestResult<Object> lastMessage(@RequestParam("userId") String userId,
                                          @RequestParam("chatId") String chatId,
                                          @RequestParam("chatType") Integer chatType) {
        String traceId = UUID.randomUUID().toString();
        ChatMessage chatMessage = chatServiceRpc.lastMessage(userId, chatId, chatType, traceId);
        if (chatMessage == null) {
            return RestResultUtils.success();
        }
        ChatMessageDto chatMessageDto = MessageConvert.pbConvert2Dto(chatMessage);
        return RestResultUtils.success(chatMessageDto);
    }
}
