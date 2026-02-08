package com.github.xuning888.helloim.contract.contant;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/21 1:48
 */
public enum GateType implements Serializable {
    TCP("tcp");

    private final String desc;

    GateType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static GateType parseGateType(String protocol) {
        GateType[] values = GateType.values();
        for (GateType value : values) {
            if (StringUtils.equals(value.getDesc(), protocol)) {
                return value;
            }
        }
        return null;
    }
}