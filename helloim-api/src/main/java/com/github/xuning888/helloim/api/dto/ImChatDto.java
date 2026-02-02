package com.github.xuning888.helloim.api.dto;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/11/8 23:22
 */
public class ImChatDto implements Serializable {
    private Long userId;
    private Long chatId;
    private Integer chatType;
    private Boolean chatTop;
    private Boolean chatMute;
    private Boolean chatDel;
    private Long updateTimestamp;
    private Long delTimestamp;
    private Long lastReadMsgId;
    private Integer subStatus;
    private Long joinGroupTimestamp;

    public Boolean getChatDel() {
        return chatDel;
    }

    public void setChatDel(Boolean chatDel) {
        this.chatDel = chatDel;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Boolean getChatMute() {
        return chatMute;
    }

    public void setChatMute(Boolean chatMute) {
        this.chatMute = chatMute;
    }

    public Boolean getChatTop() {
        return chatTop;
    }

    public void setChatTop(Boolean chatTop) {
        this.chatTop = chatTop;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Long getDelTimestamp() {
        return delTimestamp;
    }

    public void setDelTimestamp(Long delTimestamp) {
        this.delTimestamp = delTimestamp;
    }

    public Long getLastReadMsgId() {
        return lastReadMsgId;
    }

    public void setLastReadMsgId(Long lastReadMsgId) {
        this.lastReadMsgId = lastReadMsgId;
    }

    public Long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJoinGroupTimestamp() {
        return joinGroupTimestamp;
    }

    public void setJoinGroupTimestamp(Long joinGroupTimestamp) {
        this.joinGroupTimestamp = joinGroupTimestamp;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    @Override
    public String toString() {
        return "ImChatDto{" +
                "chatDel=" + chatDel +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", chatType=" + chatType +
                ", chatTop=" + chatTop +
                ", chatMute=" + chatMute +
                ", updateTimestamp=" + updateTimestamp +
                ", delTimestamp=" + delTimestamp +
                ", lastReadMsgId=" + lastReadMsgId +
                ", subStatus=" + subStatus +
                ", joinGroupTimestamp=" + joinGroupTimestamp +
                '}';
    }
}
