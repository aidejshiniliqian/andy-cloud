package com.andy.common.exception;

public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),

    FAILED(500, "操作失败"),

    VALIDATE_FAILED(401, "参数检验失败"),

    NOT_FOUND(404, "资源不存在"),

    UNAUTHORIZED(601, "暂未登录或token已经过期"),

    TOKEN_PAST(301, "token过期"),
    TOKEN_ERROR(302, "token异常"),
    // 登录异常
    LOGIN_ERROR(303, "登录异常"),
    LOGIN_LOCK(304, "用户被禁用"),

    LOGIN_NAME(305, "用户名错误"),
    LOGIN_NAME_NULL(306, "用户名为空"),
    LOGIN_PASSWORD(307, "密码错误"),
    LOGIN_CODE(308, "验证码错误"),
    LOGOUT_CODE(309, "退出失败，token 为空"),

    FORBIDDEN(403, "没有相关权限");


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
