package com.github.xuning888.helloim.contract.contant;

/**
 * @author xuning
 * @date 2025/10/4 14:11
 */
public enum RestResultStatus {

    SUCCESS(0, "success"),
    INVALID(400, "invalid params"),
    SERVER_ERR(500, "internal error"),
    ;
    private final int code;

    private final String message;

    RestResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
