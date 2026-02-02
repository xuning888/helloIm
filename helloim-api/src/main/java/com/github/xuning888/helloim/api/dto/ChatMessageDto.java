package com.github.xuning888.helloim.api.dto;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/9/21 15:53
 */
public class ChatMessageDto implements Serializable {

    private Integer chatType;
    private Long chatId;
    private String chatIdStr;
    private Long msgId;
    private String msgIdStr;
    private Long msgFrom;
    private String msgFromStr;
    private Integer fromUserType;
    private Long msgTo;
    private String msgToStr;
    private Integer toUserType;
    private Long groupId;
    private String groupIdStr;
    private Integer msgSeq;
    private String msgContent;
    private Integer contentType;
    private Integer cmdId;
    private Long sendTime;
    private Integer receiptStatus;
    private Long serverSeq;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getChatIdStr() {
        return chatIdStr;
    }

    public void setChatIdStr(String chatIdStr) {
        this.chatIdStr = chatIdStr;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getCmdId() {
        return cmdId;
    }

    public void setCmdId(Integer cmdId) {
        this.cmdId = cmdId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(Integer fromUserType) {
        this.fromUserType = fromUserType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupIdStr() {
        return groupIdStr;
    }

    public void setGroupIdStr(String groupIdStr) {
        this.groupIdStr = groupIdStr;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Long getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(Long msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMsgFromStr() {
        return msgFromStr;
    }

    public void setMsgFromStr(String msgFromStr) {
        this.msgFromStr = msgFromStr;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getMsgIdStr() {
        return msgIdStr;
    }

    public void setMsgIdStr(String msgIdStr) {
        this.msgIdStr = msgIdStr;
    }

    public Integer getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Integer msgSeq) {
        this.msgSeq = msgSeq;
    }

    public Long getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(Long msgTo) {
        this.msgTo = msgTo;
    }

    public String getMsgToStr() {
        return msgToStr;
    }

    public void setMsgToStr(String msgToStr) {
        this.msgToStr = msgToStr;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getServerSeq() {
        return serverSeq;
    }

    public void setServerSeq(Long serverSeq) {
        this.serverSeq = serverSeq;
    }

    public Integer getToUserType() {
        return toUserType;
    }

    public void setToUserType(Integer toUserType) {
        this.toUserType = toUserType;
    }

    @Override
    public String toString() {
        return "ChatMessageDto{" +
                "chatId=" + chatId +
                ", chatType=" + chatType +
                ", chatIdStr='" + chatIdStr + '\'' +
                ", msgId=" + msgId +
                ", msgIdStr='" + msgIdStr + '\'' +
                ", msgFrom=" + msgFrom +
                ", msgFromStr='" + msgFromStr + '\'' +
                ", fromUserType=" + fromUserType +
                ", msgTo=" + msgTo +
                ", msgToStr='" + msgToStr + '\'' +
                ", toUserType=" + toUserType +
                ", groupId=" + groupId +
                ", groupIdStr='" + groupIdStr + '\'' +
                ", msgSeq=" + msgSeq +
                ", msgContent='" + msgContent + '\'' +
                ", contentType=" + contentType +
                ", cmdId=" + cmdId +
                ", sendTime=" + sendTime +
                ", receiptStatus=" + receiptStatus +
                ", serverSeq=" + serverSeq +
                '}';
    }
}