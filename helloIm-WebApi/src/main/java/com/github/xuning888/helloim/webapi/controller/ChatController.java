package com.github.xuning888.helloim.webapi.controller;

import com.github.xuning888.helloim.contract.api.service.ChatService;
import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import com.github.xuning888.helloim.api.dto.ImChatDto;
import com.github.xuning888.helloim.api.dto.RestResult;
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
 * @date 2025/11/8 23:18
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @DubboReference
    private ChatService chatService;

    @GetMapping("/getAllChat")
    public RestResult<Object> getAllChat(@RequestParam("userId") String userId) {
        String traceId = UUID.randomUUID().toString();
        logger.info("/chat/getAllChat, userId: {}, traceId: {}", userId, traceId);
        List<ImChatDto> aLlChat = this.chatService.getALlChat(Long.parseLong(userId), traceId);
        return RestResultUtils.success(aLlChat);
    }

    @GetMapping("/getChat")
    public RestResult<Object> getChat(@RequestParam("userId") String userId,
                                      @RequestParam("chatId") String chatId) {
        String traceId = UUID.randomUUID().toString();
        logger.info("/chat/getChat, userId: {}, chatId: {} traceId: {}", userId, chatId, traceId);
        ImChatDto imChatDto = this.chatService.getChat(Long.parseLong(userId), Long.parseLong(chatId), traceId);
        return RestResultUtils.success(imChatDto);
    }

    @GetMapping("/lastMessage")
    public RestResult<Object> lastMessage(@RequestParam("userId") String userId,
                                          @RequestParam("chatId") String chatId,
                                          @RequestParam("chatType") Integer chatType) {
        String traceId = UUID.randomUUID().toString();
        // TODO
        ChatMessageDto chatMessageDto = chatService.lastMessage(userId, chatId, chatType, traceId);
        return RestResultUtils.success(chatMessageDto);
    }
}
