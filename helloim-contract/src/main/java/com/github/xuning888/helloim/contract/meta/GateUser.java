package com.github.xuning888.helloim.contract.meta;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author xuning
 * @date 2025/8/2 16:14
 */
public class GateUser implements Serializable {

    private Long uid;

    private Integer userType;

    private String sessionId;

    public GateUser() {
        this.userType = 0;
    }

    public GateUser(Long uid, Integer userType, String sessionId) {
        this.uid = uid;
        this.userType = userType;
        this.sessionId = sessionId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        GateUser gateUser = (GateUser) object;
        return Objects.equals(uid, gateUser.uid) && Objects.equals(userType, gateUser.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, userType);
    }

    @Override
    public String toString() {
        return String.format("%d_%s", uid, userType);
    }
}