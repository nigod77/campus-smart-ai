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
    USER_USERNAME_EXISTS(1006,"账号已存在"),
    NOT_TEACHER(1008 , "这个用户不是教师"),
    DATA_IS_OUTDATED(1007 , "数据过期"),

    CLASS_EXIST(1107 , "班级已经存在"),
    CLASS_NOT_EXIST(1108 , "班级不存在或数据库异常"),
    CLASS_HAS_STUDENTS(1109 , "该班级有学生，不可删除"),
    CLASS_DELETE_ERROR(1110,"班级删除失败"),

    TERM_ANOTHERTERM_ING(1201 , "别的学期正在进行"),
    TERM_NAME_REPLY(1202,"有重复的学期名"),
    TERM_NOT_EXIST(1203 , "班级不存在或数据库异常"),
    TERM_UPDATE_ERROR(1204,"学期删除失败"),
    TERM_PROCESSING_NOW(1205,"该学期正在进行，请先停止再删除"),
    TERM_DELETE_ERROR(1206,"学期删除失败"),

    COURSE_EXIST(1210 , "这个课程已经存在了"),
    COURSE_DELETE_ERROR(1211,"课程删除失败"),
    COURSE_NOT_EXIST(1212 , "课程不存在"),
    COURSE_INSERT_ERROR(1213 , "课程插入失败"),


    ;



    private final int code;
    private final String msg;
}
