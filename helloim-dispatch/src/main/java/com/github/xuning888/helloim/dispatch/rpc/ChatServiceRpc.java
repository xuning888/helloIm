package com.github.xuning888.helloim.dispatch.rpc;

import com.github.xuning888.helloim.chat.api.Chat;
import com.github.xuning888.helloim.chat.api.ChatService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xuning
 * @date 2025/11/2 16:24
 */
@Service
public class ChatServiceRpc {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceRpc.class);

    @DubboReference
    private ChatService chatService;

    public Chat.ServerSeqResponse serverSeq(Chat.ServerSeqRequest request) {
        logger.info("serverSeq request: {}", request);
        Chat.ServerSeqResponse response = chatService.serverSeq(request);
        logger.info("serverSeq response: {}", response);
        return response;
    }
}
