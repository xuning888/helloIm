package com.github.xuning888.helloim.webapi.convert;

import com.github.xuning888.helloim.message.api.Msg;
import com.github.xuning888.helloim.webapi.dto.ChatMessage;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/3 23:31
 */
public class WebApiConvert {

    /**
     * 将protobuf ChatMessage转换为Java ChatMessage
     */
    public static ChatMessage chatMessageFromProtobuf(Msg.ChatMessage protoChatMessage) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatType(protoChatMessage.getChatType());
        chatMessage.setMsgId(protoChatMessage.getMsgId());
        chatMessage.setMsgIdStr(String.valueOf(protoChatMessage.getMsgId()));
        chatMessage.setMsgFrom(protoChatMessage.getMsgFrom());
        chatMessage.setMsgFromStr(String.valueOf(protoChatMessage.getMsgFrom()));
        chatMessage.setFromUserType(protoChatMessage.getFromUserType());
        chatMessage.setMsgTo(protoChatMessage.getMsgTo());
        chatMessage.setMsgToStr(String.valueOf(protoChatMessage.getMsgTo()));
        chatMessage.setToUserType(protoChatMessage.getToUserType());
        chatMessage.setGroupId(protoChatMessage.getGroupId());
        chatMessage.setGroupIdStr(String.valueOf(protoChatMessage.getGroupId()));
        chatMessage.setMsgSeq(protoChatMessage.getMsgSeq());
        chatMessage.setMsgContent(protoChatMessage.getMsgContent());
        chatMessage.setContentType(protoChatMessage.getContentType());
        chatMessage.setCmdId(protoChatMessage.getCmdId());
        chatMessage.setSendTime(protoChatMessage.getSendTime());
        chatMessage.setReceiptStatus(protoChatMessage.getReceiptStatus());
        chatMessage.setServerSeq(protoChatMessage.getServerSeq());
        if (protoChatMessage.getCreatedAt() > 0) {
            chatMessage.setCreatedAt(new Date(protoChatMessage.getCreatedAt()));
        }
        if (protoChatMessage.getUpdatedAt() > 0) {
            chatMessage.setUpdatedAt(new Date(protoChatMessage.getUpdatedAt()));
        }
        return chatMessage;
    }
}
