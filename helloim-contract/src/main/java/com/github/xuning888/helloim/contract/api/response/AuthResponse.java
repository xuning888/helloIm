package com.github.xuning888.helloim.contract.api.response;

import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/10 16:18
 */
public class AuthResponse implements Serializable {

    private String errMsg;

    private boolean success;

    private String traceId;

    private GateUser gateUser;

    public AuthResponse() {
    }

    public AuthResponse(boolean success, String errMsg, GateUser gateUser, String traceId) {
        this.success = success;
        this.errMsg = errMsg;
        this.gateUser = gateUser;
        this.traceId = traceId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
}