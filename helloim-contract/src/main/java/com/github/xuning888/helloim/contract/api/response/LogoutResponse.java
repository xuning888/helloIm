package com.github.xuning888.helloim.contract.api.response;

import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 17:04
 */
public class LogoutResponse implements Serializable {

    private String traceId;

    private boolean success;

    private GateUser gateUser;

    public LogoutResponse() {
    }

    public LogoutResponse(String traceId, boolean success, GateUser gateUser) {
        this.traceId = traceId;
        this.success = success;
        this.gateUser = gateUser;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public GateUser getGateUser() {
        return gateUser;
    }

    public void setGateUser(GateUser gateUser) {
        this.gateUser = gateUser;
    }

    @Override
    public String toString() {
        return "LogoutResponse{" +
                "traceId='" + traceId + '\'' +
                ", success=" + success +
                ", gateUser=" + gateUser +
                '}';
    }
}