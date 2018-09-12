package com.zr.rail.utils;

/**
 * @author KaiZhang
 */

public enum ResultMsg {
    /* 成功状态码 */
    SUCCESS("成功"),

    /* 参数错误 */
    PARAM_IS_INVALID("参数无效"),
    PARAM_IS_BLANK("参数为空"),
    PARAM_TYPE_BIND_ERROR("参数类型错误"),
    PARAM_NOT_COMPLETE("参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGGED_IN("用户未登录"),
    USER_LOGIN_ERROR("账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN("账号已被禁用"),
    USER_NOT_EXIST("用户不存在"),
    USER_HAS_EXISTED("用户已存在"),
    USER_PASS_MSG_NOT_COMPLETE("用户密码信息缺失"),
    USER_PASS_BLANK("密码为空"),
    USER_PASS_WRONG("密码错误"),

    /* 业务错误 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST("某业务出现问题"),

    /* 系统错误 */
    SYSTEM_INNER_ERROR("系统繁忙，请稍后重试"),

    /* 数据错误 */
    RESULE_DATA_NONE("数据未找到"),
    DATA_IS_WRONG("数据有误"),
    DATA_ALREADY_EXISTED("数据已存在"),

    /* 接口错误 */
    INTERFACE_INNER_INVOKE_ERROR("内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR("外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT("该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID("接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT("接口请求超时"),
    INTERFACE_EXCEED_LOAD("接口负载过高"),

    /* 权限错误 */
    PERMISSION_NO_ACCESS("无访问权限");

    ResultMsg(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    private String msg;
}
