package com.github.xuning888.helloim.contract.dto;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.frame.Header;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import io.protostuff.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/8/2 16:22
 */
public class MsgContext implements Serializable {

    public static String GROUP_MEMBER_KEY = "members";

    /**
     * 消息发送方,
     */
    @Tag(value = 1)
    private String msgFrom;

    /**
     * 消息的接收方
     */
    @Tag(value = 2)
    private String msgTo;

    /**
     * 消息ID
     */
    @Tag(value = 3)
    private Long msgId;

    /**
     * 服务端分配的消息序号
     */
    @Tag(value = 4)
    private Long serverSeq;

    /**
     * 长连接网关的地址
     */
    @Tag(value = 5)
    private Endpoint endpoint;

    /**
     * 数据帧
     */
    @Tag(value = 6)
    private Frame frame;

    /**
     * traceId
     */
    @Tag(value = 7)
    private String traceId;

    /**
     * 消息扩展字段
     */
    @Tag(value = 8)
    private final Map<String, Object> contextMap = new HashMap<>();

    @Tag(value =  9)
    private long timestamp;

    @Tag(value = 10)
    private Integer fromUserType;

    @Tag(value = 11)
    private Integer toUserType;

    @Tag(value = 12)
    private String sessionId;

    /**
     * 群聊ID
     */
    @Tag(value = 13)
    private long groupId;

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getServerSeq() {
        return serverSeq;
    }

    public void setServerSeq(Long serverSeq) {
        this.serverSeq = serverSeq;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public <T> T getFromContext(String key) {
        Object value = this.contextMap.get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public void putContext(String key, Object object) {
        this.contextMap.put(key, object);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(Integer fromUserType) {
        this.fromUserType = fromUserType;
    }

    public Integer getToUserType() {
        return toUserType;
    }

    public void setToUserType(Integer toUserType) {
        this.toUserType = toUserType;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getMsgSeq() {
        if (this.frame == null) {
            return 0;
        }
        Header header = frame.getHeader();
        if (header == null) {
            return 0;
        }
        return header.getSeq();
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
