package com.github.xuning888.helloim.message.convert;

import com.github.xuning888.helloim.contract.dto.ChatMessage;
import com.github.xuning888.helloim.message.api.Msg;
import com.github.xuning888.helloim.store.api.Store;

/**
 * @author xuning
 * @date 2025/11/2 21:09
 */
public class MessageConvert {

    public static Store.ChatMessage convert2StoreChatMessage(Msg.ChatMessage chatMessage) {
        Store.ChatMessage.Builder builder = Store.ChatMessage.newBuilder();
        builder.setChatType(chatMessage.getChatType());
        builder.setMsgId(chatMessage.getMsgId());
        builder.setMsgFrom(chatMessage.getMsgFrom());
        builder.setFromUserType(chatMessage.getFromUserType());
        builder.setMsgTo(chatMessage.getMsgTo());
        builder.setToUserType(chatMessage.getToUserType());
        builder.setGroupId(chatMessage.getGroupId());
        builder.setMsgSeq(chatMessage.getMsgSeq());
        builder.setMsgContent(chatMessage.getMsgContent());
        builder.setContentType(chatMessage.getContentType());
        builder.setCmdId(chatMessage.getCmdId());
        builder.setSendTime(chatMessage.getSendTime());
        builder.setReceiptStatus(chatMessage.getReceiptStatus());
        builder.setServerSeq(chatMessage.getServerSeq());
        return builder.build();
    }
}
