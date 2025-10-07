package com.github.xuning888.helloim.contract.api.request;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/10/7 02:06
 */
public class PullOfflineMsgRequest implements Serializable {

    private Long fromUserId;

    private Long chatId;

    private Integer chatType;

    private Long minServerSeq;

    private Long maxServerSeq;

    private Integer size;

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

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getMaxServerSeq() {
        return maxServerSeq;
    }

    public void setMaxServerSeq(Long maxServerSeq) {
        this.maxServerSeq = maxServerSeq;
    }

    public Long getMinServerSeq() {
        return minServerSeq;
    }

    public void setMinServerSeq(Long minServerSeq) {
        this.minServerSeq = minServerSeq;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PullOfflineMsgRequest{" +
                "chatId=" + chatId +
                ", fromUserId=" + fromUserId +
                ", chatType=" + chatType +
                ", minServerSeq=" + minServerSeq +
                ", maxServerSeq=" + maxServerSeq +
                ", size=" + size +
                '}';
    }
}
