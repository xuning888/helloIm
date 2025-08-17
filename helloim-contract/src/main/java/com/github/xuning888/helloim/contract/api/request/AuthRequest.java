package com.github.xuning888.helloim.contract.api.request;

import com.github.xuning888.helloim.contract.meta.Endpoint;
import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 16:17
 */
public class AuthRequest implements Serializable {

    /**
     * 用户信息
     */
    private GateUser gateUser;

    /**
     * 网关地址
     */
    private Endpoint endpoint;

    /**
     * channel的sessionId
     */
    private String sessionId;

    private String traceId;


    public AuthRequest() {}

    public AuthRequest(GateUser gateUser, Endpoint endpoint, String sessionId, String traceId) {
        this.gateUser = gateUser;
        this.endpoint = endpoint;
        this.sessionId = sessionId;
        this.traceId = traceId;
    }


    public GateUser getGateUser() {
        return gateUser;
    }

    public void setGateUser(GateUser gateUser) {
        this.gateUser = gateUser;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}