package com.github.xuning888.helloim.contract.api.request;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuning
 * @date 2025/8/3 13:20
 */
public class DownMessageReq implements Serializable {

    private Frame frame;

    private boolean needAck;

    private List<GateUser> users;

    private String traceId;

    public DownMessageReq() {}

    public DownMessageReq(Frame frame, List<GateUser> users, String traceId) {
        this(frame, false, users, traceId);
    }

    public DownMessageReq(Frame frame, boolean needAck, List<GateUser> users, String traceId) {
        this.frame = frame;
        this.needAck = needAck;
        this.users = users;
        this.traceId = traceId;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public List<GateUser> getUsers() {
        return users;
    }

    public void setUsers(List<GateUser> users) {
        this.users = users;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public boolean getNeedAck() {
        return needAck;
    }

    public void setNeedAck(boolean needAck) {
        this.needAck = needAck;
    }

    @Override
    public String toString() {
        return "DownMessageReq{" +
                ", needAck=" + needAck +
                ", users=" + users +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}