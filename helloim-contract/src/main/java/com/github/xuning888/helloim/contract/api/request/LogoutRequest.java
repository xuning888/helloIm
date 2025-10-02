package com.github.xuning888.helloim.contract.api.request;

import com.github.xuning888.helloim.contract.contant.GateSessionEvent;
import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 17:03
 */
public class LogoutRequest implements Serializable {

    private String traceId;

    private GateUser gateUser;

    private Endpoint endpoint;

    private GateSessionEvent sessionEvent;

    private String sessionId;

    public LogoutRequest() {
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public GateUser getGateUser() {
        return gateUser;
    }

    public void setGateUser(GateUser gateUser) {
        this.gateUser = gateUser;
    }

    public GateSessionEvent getSessionEvent() {
        return sessionEvent;
    }

    public void setSessionEvent(GateSessionEvent sessionEvent) {
        this.sessionEvent = sessionEvent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "traceId='" + traceId + '\'' +
                ", gateUser=" + gateUser +
                ", sessionEvent=" + sessionEvent +
                '}';
    }
}