package com.github.xuning888.helloim.contract.convert;

import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.ChatMessageDto;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/9 21:00
 */
public class MessageConvert {


    public static ChatMessageDto buildC2CChatMessage(MsgContext msgContext, C2cMessage.C2cSendRequest c2cSendRequest) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setChatType(ChatType.C2C.getType());
        chatMessageDto.setChatId(Long.parseLong(c2cSendRequest.getTo()));
        chatMessageDto.setChatIdStr(c2cSendRequest.getTo());
        chatMessageDto.setMsgId(msgContext.getMsgId());
        chatMessageDto.setMsgIdStr(msgContext.getMsgId().toString());
        chatMessageDto.setMsgFrom(Long.parseLong(c2cSendRequest.getFrom()));
        chatMessageDto.setFromUserType(msgContext.getFromUserType());
        chatMessageDto.setMsgFromStr(c2cSendRequest.getFrom());
        chatMessageDto.setMsgTo(Long.parseLong(c2cSendRequest.getTo()));
        chatMessageDto.setMsgToStr(c2cSendRequest.getTo());
        chatMessageDto.setToUserType(msgContext.getToUserType());
        chatMessageDto.setGroupId(null);
        chatMessageDto.setGroupIdStr(StringUtils.EMPTY);
        Frame frame = msgContext.getFrame();
        chatMessageDto.setMsgSeq(frame.getHeader().getSeq());
        chatMessageDto.setMsgContent(c2cSendRequest.getContent());
        chatMessageDto.setCmdId(frame.getHeader().getCmdId());
        chatMessageDto.setSendTime(msgContext.getTimestamp()); // 时间不准确
        chatMessageDto.setReceiptStatus(0);
        chatMessageDto.setServerSeq(msgContext.getServerSeq());
        return chatMessageDto;
    }

    public static ImMessage convertImMessage(ChatMessageDto chatMessageDto) {
        ImMessage imMessage = new ImMessage();
        imMessage.setMsgId(chatMessageDto.getMsgId());
        imMessage.setMsgFrom(chatMessageDto.getMsgFrom());
        imMessage.setFromUserType(chatMessageDto.getFromUserType());
        imMessage.setMsgTo(chatMessageDto.getMsgTo());
        imMessage.setToUserType(chatMessageDto.getToUserType());
        imMessage.setMsgSeq(chatMessageDto.getMsgSeq());
        imMessage.setMsgContent(chatMessageDto.getMsgContent());
        imMessage.setContentType(chatMessageDto.getContentType());
        imMessage.setCmdId(chatMessageDto.getCmdId());
        imMessage.setSendTime(new Date(chatMessageDto.getSendTime()));
        imMessage.setServerSeq(chatMessageDto.getServerSeq());
        imMessage.setReceiptStatus(chatMessageDto.getReceiptStatus());
        return imMessage;
    }

    public static ImMessageGroup convertImMessageGroup(ChatMessageDto chatMessageDto) {
        ImMessageGroup imMessageGroup = new ImMessageGroup();
        imMessageGroup.setMsgId(chatMessageDto.getMsgId());
        imMessageGroup.setMsgFrom(chatMessageDto.getMsgFrom());
        imMessageGroup.setFromUserType(chatMessageDto.getFromUserType());
        imMessageGroup.setGroupId(chatMessageDto.getGroupId());
        imMessageGroup.setMsgSeq(chatMessageDto.getMsgSeq());
        imMessageGroup.setMsgContent(chatMessageDto.getMsgContent());
        imMessageGroup.setCmdId(chatMessageDto.getCmdId());
        imMessageGroup.setSendTime(new Date(chatMessageDto.getSendTime()));
        imMessageGroup.setServerSeq(chatMessageDto.getServerSeq());
        return imMessageGroup;
    }

    public static ChatMessageDto convert2ChatMessage(ImMessage imMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setChatType(ChatType.C2C.getType());
        chatMessageDto.setChatId(imMessage.getMsgTo());
        chatMessageDto.setChatIdStr(imMessage.getMsgTo().toString());
        chatMessageDto.setMsgId(imMessage.getMsgId());
        chatMessageDto.setMsgIdStr(imMessage.getMsgId().toString());
        chatMessageDto.setMsgFrom(imMessage.getMsgFrom());
        chatMessageDto.setMsgFromStr(imMessage.getMsgFrom().toString());
        chatMessageDto.setFromUserType(imMessage.getFromUserType());
        chatMessageDto.setMsgTo(imMessage.getMsgTo());
        chatMessageDto.setMsgToStr(imMessage.getMsgTo().toString());
        chatMessageDto.setToUserType(imMessage.getToUserType());
        chatMessageDto.setGroupId(0L);
        chatMessageDto.setGroupIdStr(StringUtils.EMPTY);
        chatMessageDto.setMsgSeq(imMessage.getMsgSeq());
        chatMessageDto.setMsgContent(imMessage.getMsgContent());
        chatMessageDto.setContentType(imMessage.getContentType());
        chatMessageDto.setCmdId(imMessage.getCmdId());
        chatMessageDto.setSendTime(imMessage.getSendTime().getTime());
        chatMessageDto.setReceiptStatus(imMessage.getReceiptStatus());
        chatMessageDto.setServerSeq(imMessage.getServerSeq());
        return chatMessageDto;
    }

    public static ChatMessageDto convert2ChatMessage(ImMessageGroup imMessageGroup) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setChatType(ChatType.C2G.getType());
        chatMessageDto.setChatId(imMessageGroup.getGroupId());
        chatMessageDto.setChatIdStr(imMessageGroup.getGroupId().toString());
        chatMessageDto.setMsgId(imMessageGroup.getMsgId());
        chatMessageDto.setMsgIdStr(imMessageGroup.getMsgId().toString());
        chatMessageDto.setMsgFrom(imMessageGroup.getMsgFrom());
        chatMessageDto.setMsgFromStr(imMessageGroup.getMsgFrom().toString());
        chatMessageDto.setFromUserType(imMessageGroup.getFromUserType());
        chatMessageDto.setMsgTo(imMessageGroup.getGroupId());
        chatMessageDto.setMsgToStr(imMessageGroup.getGroupId().toString());
        chatMessageDto.setToUserType(0);
        chatMessageDto.setGroupId(imMessageGroup.getGroupId());
        chatMessageDto.setGroupIdStr(imMessageGroup.getGroupId().toString());
        chatMessageDto.setMsgSeq(imMessageGroup.getMsgSeq());
        chatMessageDto.setMsgContent(imMessageGroup.getMsgContent());
        chatMessageDto.setContentType(imMessageGroup.getContentType());
        chatMessageDto.setCmdId(imMessageGroup.getCmdId());
        chatMessageDto.setSendTime(imMessageGroup.getSendTime().getTime());
        chatMessageDto.setReceiptStatus(0); // 群聊不关注
        chatMessageDto.setServerSeq(imMessageGroup.getServerSeq());
        return chatMessageDto;
    }
}
