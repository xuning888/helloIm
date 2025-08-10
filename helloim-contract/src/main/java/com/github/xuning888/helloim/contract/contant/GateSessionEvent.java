package com.github.xuning888.helloim.contract.contant;

import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 15:42
 */
public class GateSessionEvent implements Serializable {

    private GateSessionState sessionState;

    private GateUser gateUser;

    private String traceId;

    public GateSessionEvent() {}

    public GateSessionEvent(GateSessionState sessionState, GateUser gateUser, String traceId) {
        this.sessionState = sessionState;
        this.gateUser = gateUser;
        this.traceId = traceId;
    }

    public GateSessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(GateSessionState sessionState) {
        this.sessionState = sessionState;
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
        return "GateSessionEvent{" +
                "sessionState=" + sessionState +
                ", gateUser=" + gateUser +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}