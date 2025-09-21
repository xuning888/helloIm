package com.github.xuning888.helloim.contract.entity;

import java.io.Serializable;
import java.util.Date;

public class ImMessageGroup implements Serializable {
    private Long msgId;

    private Long msgFrom;

    private Integer fromUserType;

    private Long groupId;

    private Integer msgSeq;

    private String msgContent;

    private Integer contentType;

    private Integer cmdId;

    private Date sendTime;

    private Long serverSeq;

    private Date createdAt;

    private Date updatedAt;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(Long msgFrom) {
        this.msgFrom = msgFrom;
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

    public Integer getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Integer msgSeq) {
        this.msgSeq = msgSeq;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getCmdId() {
        return cmdId;
    }

    public void setCmdId(Integer cmdId) {
        this.cmdId = cmdId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getServerSeq() {
        return serverSeq;
    }

    public void setServerSeq(Long serverSeq) {
        this.serverSeq = serverSeq;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}