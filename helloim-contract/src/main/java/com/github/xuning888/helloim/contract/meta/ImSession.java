package com.github.xuning888.helloim.contract.meta;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/21 1:43
 */
public class ImSession implements Serializable {

    /**
     * 长连接网关的中Channel的ID
     */
    private String sessionId;

    /**
     * 长连接网关的地址
     */
    private Endpoint endpoint;

    /**
     * 用户信息
     */
    private GateUser gateUser;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    @Override
    public String toString() {
        return "ImSession{" +
                "sessionId='" + sessionId + '\'' +
                ", endpoint=" + endpoint +
                ", gateUser=" + gateUser +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ImSession imSession = (ImSession) object;
        return Objects.equals(sessionId, imSession.sessionId) && Objects.equals(endpoint, imSession.endpoint) && Objects.equals(gateUser, imSession.gateUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, endpoint, gateUser);
    }
}