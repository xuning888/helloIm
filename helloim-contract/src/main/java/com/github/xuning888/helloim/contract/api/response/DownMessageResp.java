package com.github.xuning888.helloim.contract.api.response;


import com.github.xuning888.helloim.contract.meta.GateUser;

import java.io.Serializable;
import java.util.Set;

/**
 * @author xuning
 * @date 2026/1/26 01:29
 */
public class DownMessageResp implements Serializable {

    // 网关不在线的用户
    private Set<GateUser> offlineUsers;

    private String traceId;

    public DownMessageResp() {}

    public DownMessageResp(Set<GateUser> offlineUser, String traceId) {
        this.offlineUsers = offlineUser;
        this.traceId = traceId;
    }

    public Set<GateUser> getOfflineUsers() {
        return offlineUsers;
    }

    public void setOfflineUsers(Set<GateUser> offlineUsers) {
        this.offlineUsers = offlineUsers;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
