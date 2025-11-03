package com.github.xuning888.helloim.webapi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuning
 * @date 2025/11/3 23:29
 */
public class ChatMessage implements Serializable {

    private Integer chatType;
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
    private Date createdAt;
    private Date updatedAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGroupIdStr() {
        return groupIdStr;
    }

    public void setGroupIdStr(String groupIdStr) {
        this.groupIdStr = groupIdStr;
    }

    public String getMsgFromStr() {
        return msgFromStr;
    }

    public void setMsgFromStr(String msgFromStr) {
        this.msgFromStr = msgFromStr;
    }

    public String getMsgIdStr() {
        return msgIdStr;
    }

    public void setMsgIdStr(String msgIdStr) {
        this.msgIdStr = msgIdStr;
    }

    public String getMsgToStr() {
        return msgToStr;
    }

    public void setMsgToStr(String msgToStr) {
        this.msgToStr = msgToStr;
    }
}
