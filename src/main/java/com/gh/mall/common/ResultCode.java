package com.gh.mall.common;

public enum ResultCode {
    SUCCESS("0", "成功"),
    ERROR("1", "系统异常"),
    PARAM_ERROR("1001", "参数异常"),
    USER_EXIST_ERROR("2001", "用户已存在"),
    USER_ACCOUNT_ERROR("2002", "账户或密码错误"),
    USER_NOT_EXIST_ERROR("2003","未找到用户");
    public String code;
    public String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
