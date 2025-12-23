package com.nijiahao.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200 , "操作成功"),
    ERROR(500 , "系统内部异常"),
    UNAUTHORIZED(401 , "未授权或登陆已过期"),
    FORBIDDEN(403 , "无权限访问"),
    PARAM_ERROR(400 , "参数错误"),

    // 业务具体错误（这里放你的登录提示）
    LOGIN_ERROR(1001, "账号或密码错误"),
    USER_NOT_EXIST(1002, "用户不存在"),
    USER_LOCKED(1003, "账号已被锁定"),
    USER_UPDATE_ERROR(1004,"该用户不存在或数据库错误"),
    USER_DELETE_ERROR(1005,"请选择要删除的用户"),
    USER_USERNAME_EXISTS(1004,"账号已存在");

    private final int code;
    private final String msg;
}
