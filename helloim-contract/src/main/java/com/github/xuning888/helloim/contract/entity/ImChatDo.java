package com.github.xuning888.helloim.contract.entity;

import java.io.Serializable;
import java.util.Date;

public class ImChatDo implements Serializable {
    private Long id;

    private Long userId;

    private Long chatId;

    private Integer chatType;

    private Integer chatTop;

    private Integer chatDel;

    private Date updateTimestamp;

    private Date delTimestamp;

    private Integer chatMute;

    private Integer subStatus;

    private Date joinGroupTimestamp;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getChatTop() {
        return chatTop;
    }

    public void setChatTop(Integer chatTop) {
        this.chatTop = chatTop;
    }

    public Integer getChatDel() {
        return chatDel;
    }

    public void setChatDel(Integer chatDel) {
        this.chatDel = chatDel;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getDelTimestamp() {
        return delTimestamp;
    }

    public void setDelTimestamp(Date delTimestamp) {
        this.delTimestamp = delTimestamp;
    }

    public Integer getChatMute() {
        return chatMute;
    }

    public void setChatMute(Integer chatMute) {
        this.chatMute = chatMute;
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

    public Date getJoinGroupTimestamp() {
        return joinGroupTimestamp;
    }

    public void setJoinGroupTimestamp(Date joinGroupTimestamp) {
        this.joinGroupTimestamp = joinGroupTimestamp;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }
}