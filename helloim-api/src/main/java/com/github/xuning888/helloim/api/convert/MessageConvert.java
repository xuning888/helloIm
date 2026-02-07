package com.github.xuning888.helloim.api.convert;


import com.github.xuning888.helloim.api.dto.ChatMessageDto;
import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuning
 * @date 2026/2/7 16:10
 */
public class MessageConvert {

    public static ChatMessageDto pbConvert2Dto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setChatType(chatMessage.getChatType());
        chatMessageDto.setChatId(chatMessage.getChatId());
        chatMessageDto.setMsgIdStr(chatMessage.getChatIdStr());
        chatMessageDto.setMsgId(chatMessage.getMsgId());
        chatMessageDto.setMsgIdStr(chatMessage.getMsgIdStr());
        chatMessageDto.setMsgFrom(chatMessage.getMsgFrom());
        chatMessageDto.setMsgFromStr(chatMessage.getMsgFromStr());
        chatMessageDto.setFromUserType(chatMessage.getFromUserType());
        chatMessageDto.setMsgTo(chatMessageDto.getMsgTo());
        chatMessageDto.setMsgToStr(chatMessage.getMsgToStr());
        chatMessageDto.setToUserType(chatMessage.getToUserType());
        chatMessageDto.setGroupId(chatMessage.getGroupId());
        chatMessageDto.setGroupIdStr(chatMessage.getGroupIdStr());
        chatMessageDto.setMsgSeq(chatMessage.getMsgSeq());
        chatMessageDto.setMsgContent(chatMessage.getMsgContent());
        chatMessageDto.setContentType(chatMessage.getContentType());
        chatMessageDto.setCmdId(chatMessage.getCmdId());
        chatMessageDto.setSendTime(chatMessage.getSendTime());
        chatMessageDto.setReceiptStatus(chatMessage.getReceiptStatus());
        chatMessageDto.setServerSeq(chatMessage.getServerSeq());
        return chatMessageDto;
    }

    public static List<ChatMessageDto> pbConvert2Dto(List<ChatMessage> chatMessages) {
        List<ChatMessageDto> messageDtos = new ArrayList<>(chatMessages.size());
        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageDto chatMessageDto = pbConvert2Dto(chatMessage);
            messageDtos.add(chatMessageDto);
        }
        return messageDtos;
    }
}
