package com.github.xuning888.helloim.contract.exception;


import com.github.xuning888.helloim.contract.contant.RestResultStatus;

/**
 * @author xuning
 * @date 2026/2/1 12:35
 */
public class ImException extends RuntimeException {

    private int code;

    private String message;

    public ImException() {
    }

    public ImException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ImException(int code, String message, Throwable ex) {
        super(message, ex);
        this.code = code;
        this.message = message;
    }

    public ImException(RestResultStatus restResultStatus, Throwable ex) {
        super(restResultStatus.getMessage(), ex);
        this.code = restResultStatus.getCode();
        this.message = restResultStatus.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
