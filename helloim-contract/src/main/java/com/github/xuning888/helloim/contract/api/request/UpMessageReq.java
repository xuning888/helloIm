package com.github.xuning888.helloim.contract.api.request;

import com.github.xuning888.helloim.contract.frame.Frame;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/2 16:09
 */
public class UpMessageReq implements Serializable {

    private Frame frame;

    private Endpoint endpoint;

    private GateUser gateUser;

    private String traceId;

    public UpMessageReq() {}

    public UpMessageReq(Frame frame, Endpoint endpoint, GateUser gateUser, String traceId) {
        this.frame = frame;
        this.endpoint = endpoint;
        this.gateUser = gateUser;
        this.traceId = traceId;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public GateUser getGateUser() {
        return gateUser;
    }

    public void setGateUser(GateUser gateUser) {
        this.gateUser = gateUser;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "UpMessageReq{" +
                "frame=" + frame +
                ", endpoint=" + endpoint +
                ", gateUser=" + gateUser +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}