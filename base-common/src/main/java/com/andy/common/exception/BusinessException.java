package com.andy.common.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    private int code = -1;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
