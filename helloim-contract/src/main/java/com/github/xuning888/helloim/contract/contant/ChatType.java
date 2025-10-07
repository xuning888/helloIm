package com.github.xuning888.helloim.contract.contant;

import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/8/2 19:30
 */
public enum ChatType implements Serializable {


    C2C(1, "单聊会话"),

    C2G(2, "群聊会话");

    private final Integer type;
    private final String description;

    ChatType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public boolean match(Integer type) {
        if (type == null) {
            return false;
        }
        return this.type.equals(type);
    }
}
