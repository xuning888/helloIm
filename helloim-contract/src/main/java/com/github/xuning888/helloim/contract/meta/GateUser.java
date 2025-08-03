package com.github.xuning888.helloim.contract.meta;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/2 16:14
 */
public class GateUser implements Serializable {

    private Long uid;

    private Integer userType;

    public GateUser() {
        this.uid = null;
        this.userType = 0;
    }

    public GateUser(Long uid) {
        this.uid = uid;
        this.userType = 0;
    }

    public GateUser(Long uid, Integer userType) {
        this.uid = uid;
        this.userType = userType;
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

    @Override
    public String toString() {
        return String.format("GateUser: %d_%s", uid, userType);
    }
}