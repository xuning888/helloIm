package com.github.xuning888.helloim.webapi.rpc;

import com.github.xuning888.helloim.message.api.MessageService;
import com.github.xuning888.helloim.message.api.Msg;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author xuning
 * @date 2025/11/2 22:07
 */
@Component
public class MessageServiceRpc {

    @DubboReference
    public MessageService messageService;

    public Msg.ChatMessageList pullOfflineMsg(Msg.PullOfflineMsgRequest request) {
        return messageService.pullOfflineMsg(request);
    }

    public Msg.ChatMessageList getLatestOfflineMessages(Msg.PullOfflineMsgRequest request) {
        return messageService.getLatestOfflineMessages(request);
    }
}
