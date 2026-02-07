package com.github.xuning888.helloim.contract.convert;

import com.github.xuning888.helloim.api.protobuf.common.v1.ChatMessage;
import com.github.xuning888.helloim.contract.contant.ChatType;
import com.github.xuning888.helloim.contract.dto.MsgContext;
import com.github.xuning888.helloim.contract.entity.ImMessage;
import com.github.xuning888.helloim.contract.entity.ImMessageGroup;
import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.protobuf.C2cMessage;
import com.github.xuning888.helloim.contract.protobuf.C2gMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author xuning
 * @date 2025/11/9 21:00
 */
public class MessageConvert {

    public static ChatMessage buildC2CChatMessage(MsgContext msgContext, C2cMessage.C2cSendRequest c2cSendRequest) {
        ChatMessage.Builder builder = ChatMessage.newBuilder();
        builder.setChatType(ChatType.C2C.getType());
        builder.setChatId(Long.parseLong(c2cSendRequest.getTo()));
        builder.setChatIdStr(c2cSendRequest.getTo());
        builder.setMsgId(msgContext.getMsgId());
        builder.setMsgIdStr(msgContext.getMsgId().toString());
        builder.setMsgFrom(Long.parseLong(c2cSendRequest.getFrom()));
        builder.setFromUserType(msgContext.getFromUserType());
        builder.setMsgFromStr(c2cSendRequest.getFrom());
        builder.setMsgTo(Long.parseLong(c2cSendRequest.getTo()));
        builder.setMsgToStr(c2cSendRequest.getTo());
        builder.setToUserType(msgContext.getToUserType());
        builder.setGroupId(0L);
        builder.setGroupIdStr(StringUtils.EMPTY);
        Frame frame = msgContext.getFrame();
        builder.setMsgSeq(frame.getHeader().getSeq());
        builder.setMsgContent(c2cSendRequest.getContent());
        builder.setCmdId(frame.getHeader().getCmdId());
        builder.setSendTime(msgContext.getTimestamp()); // 时间不准确
        builder.setReceiptStatus(0);
        builder.setServerSeq(msgContext.getServerSeq());
        return builder.build();
    }

    public static ImMessage convertImMessage(ChatMessage chatMessage) {
        ImMessage imMessage = new ImMessage();
        imMessage.setMsgId(chatMessage.getMsgId());
        imMessage.setMsgFrom(chatMessage.getMsgFrom());
        imMessage.setFromUserType(chatMessage.getFromUserType());
        imMessage.setMsgTo(chatMessage.getMsgTo());
        imMessage.setToUserType(chatMessage.getToUserType());
        imMessage.setMsgSeq(chatMessage.getMsgSeq());
        imMessage.setMsgContent(chatMessage.getMsgContent());
        imMessage.setContentType(chatMessage.getContentType());
        imMessage.setCmdId(chatMessage.getCmdId());
        imMessage.setSendTime(new Date(chatMessage.getSendTime()));
        imMessage.setServerSeq(chatMessage.getServerSeq());
        imMessage.setReceiptStatus(chatMessage.getReceiptStatus());
        return imMessage;
    }

    public static ImMessageGroup convertImMessageGroup(ChatMessage chatMessage) {
        ImMessageGroup imMessageGroup = new ImMessageGroup();
        imMessageGroup.setMsgId(chatMessage.getMsgId());
        imMessageGroup.setMsgFrom(chatMessage.getMsgFrom());
        imMessageGroup.setFromUserType(chatMessage.getFromUserType());
        imMessageGroup.setGroupId(chatMessage.getGroupId());
        imMessageGroup.setMsgSeq(chatMessage.getMsgSeq());
        imMessageGroup.setMsgContent(chatMessage.getMsgContent());
        imMessageGroup.setCmdId(chatMessage.getCmdId());
        imMessageGroup.setSendTime(new Date(chatMessage.getSendTime()));
        imMessageGroup.setServerSeq(chatMessage.getServerSeq());
        return imMessageGroup;
    }

    public static ChatMessage convert2ChatMessage(ImMessage imMessage) {
        ChatMessage.Builder builder = ChatMessage.newBuilder();
        builder.setChatType(ChatType.C2C.getType());
        builder.setChatId(imMessage.getMsgTo());
        builder.setChatIdStr(imMessage.getMsgTo().toString());
        builder.setMsgId(imMessage.getMsgId());
        builder.setMsgIdStr(imMessage.getMsgId().toString());
        builder.setMsgFrom(imMessage.getMsgFrom());
        builder.setMsgFromStr(imMessage.getMsgFrom().toString());
        builder.setFromUserType(imMessage.getFromUserType());
        builder.setMsgTo(imMessage.getMsgTo());
        builder.setMsgToStr(imMessage.getMsgTo().toString());
        builder.setToUserType(imMessage.getToUserType());
        builder.setGroupId(0L);
        builder.setGroupIdStr(StringUtils.EMPTY);
        builder.setMsgSeq(imMessage.getMsgSeq());
        builder.setMsgContent(imMessage.getMsgContent());
        builder.setContentType(imMessage.getContentType());
        builder.setCmdId(imMessage.getCmdId());
        builder.setSendTime(imMessage.getSendTime().getTime());
        builder.setReceiptStatus(imMessage.getReceiptStatus());
        builder.setServerSeq(imMessage.getServerSeq());
        return builder.build();
    }

    public static ChatMessage convert2ChatMessage(ImMessageGroup imMessageGroup) {
        ChatMessage.Builder builder = ChatMessage.newBuilder();
        builder.setChatType(ChatType.C2G.getType());
        builder.setChatId(imMessageGroup.getGroupId());
        builder.setChatIdStr(imMessageGroup.getGroupId().toString());
        builder.setMsgId(imMessageGroup.getMsgId());
        builder.setMsgIdStr(imMessageGroup.getMsgId().toString());
        builder.setMsgFrom(imMessageGroup.getMsgFrom());
        builder.setMsgFromStr(imMessageGroup.getMsgFrom().toString());
        builder.setFromUserType(imMessageGroup.getFromUserType());
        builder.setMsgTo(imMessageGroup.getGroupId());
        builder.setMsgToStr(imMessageGroup.getGroupId().toString());
        builder.setToUserType(0);
        builder.setGroupId(imMessageGroup.getGroupId());
        builder.setGroupIdStr(imMessageGroup.getGroupId().toString());
        builder.setMsgSeq(imMessageGroup.getMsgSeq());
        builder.setMsgContent(imMessageGroup.getMsgContent());
        builder.setContentType(imMessageGroup.getContentType());
        builder.setCmdId(imMessageGroup.getCmdId());
        builder.setSendTime(imMessageGroup.getSendTime().getTime());
        builder.setReceiptStatus(0); // 群聊不关注
        builder.setServerSeq(imMessageGroup.getServerSeq());
        return builder.build();
    }

    public static List<ChatMessage> convert2ChatMessages(List<ImMessageGroup> imMessageGroups) {
        if (CollectionUtils.isEmpty(imMessageGroups)) {
            return Collections.emptyList();
        }
        List<ChatMessage> chatMessages = new ArrayList<>(imMessageGroups.size());
        for (ImMessageGroup imMessageGroup : imMessageGroups) {
            ChatMessage chatMessage = convert2ChatMessage(imMessageGroup);
            chatMessages.add(chatMessage);
        }
        return chatMessages;
    }

    public static List<ChatMessage> convertImMessages2ChatMessages(List<ImMessage> imMessages) {
        if (CollectionUtils.isEmpty(imMessages)) {
            return Collections.emptyList();
        }
        List<ChatMessage> chatMessages = new ArrayList<>(imMessages.size());
        for (ImMessage imMessage : imMessages) {
            ChatMessage chatMessage = convert2ChatMessage(imMessage);
            chatMessages.add(chatMessage);
        }
        return chatMessages;
    }

    public static ChatMessage buildC2GChatMessage(MsgContext msgContext, C2gMessage.C2GSendRequest c2gSendRequest){
        ChatMessage.Builder builder = ChatMessage.newBuilder();
        builder.setChatType(ChatType.C2G.getType()); // 群聊
        builder.setChatId(msgContext.getGroupId()); // 群聊的话groupId就是chatId
        builder.setChatIdStr(String.valueOf(msgContext.getGroupId()));
        builder.setMsgId(msgContext.getMsgId()); // 消息ID
        builder.setMsgIdStr(String.valueOf(msgContext.getMsgId()));
        builder.setMsgFrom(Long.parseLong(msgContext.getMsgFrom()));
        builder.setMsgFromStr(msgContext.getMsgFrom());
        builder.setFromUserType(msgContext.getFromUserType());
        builder.setMsgTo(msgContext.getGroupId());
        builder.setMsgToStr(String.valueOf(msgContext.getGroupId()));
        builder.setToUserType(0); // 群聊不关注这个字段
        builder.setGroupId(msgContext.getGroupId());
        builder.setGroupIdStr(String.valueOf(msgContext.getGroupId()));
        builder.setMsgSeq(msgContext.getMsgSeq());
        builder.setMsgContent(c2gSendRequest.getContent());
        builder.setContentType(c2gSendRequest.getContentType());
        Frame frame = msgContext.getFrame();
        Header header = frame.getHeader();
        builder.setCmdId(header.getCmdId());
        builder.setSendTime(msgContext.getTimestamp());
        builder.setReceiptStatus(0);
        builder.setServerSeq(msgContext.getServerSeq());
        return builder.build();
    }
}
