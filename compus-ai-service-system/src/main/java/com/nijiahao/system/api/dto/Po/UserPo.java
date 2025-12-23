package com.nijiahao.system.api.dto.Po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nijiahao.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("campus_sys_user")
public class UserPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 锁版本号 (乐观锁)
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;


    /**
     * 是否启用
     * true/1-启用 false/0-禁用
     */
    @TableField(fill = FieldFill.INSERT)
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 身份
     * 0-管理员 1-学生 2-老师
     */
    private Integer identity;

    /**
     * 用户名 (唯一)
     */
    private String userName;

    /**
     * 密码 (加密存储)
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像url
     */
    private String image;

    /**
     * 性别
     * (建议定义枚举或者 0-未知 1-男 2-女)
     */
    private Integer gender;

    /**
     * 学号/工号
     */
    private String workId;

}
