package com.github.xuning888.helloim.contract.contant;

/**
 * @author xuning
 * @date 2025/11/10 00:25
 */
public enum ChatSubStatus {
    IN_GROUP(0),
    EXIT_GROUP(1),
    DISSOLVE_GROUP(2);
    private int code;

    ChatSubStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
