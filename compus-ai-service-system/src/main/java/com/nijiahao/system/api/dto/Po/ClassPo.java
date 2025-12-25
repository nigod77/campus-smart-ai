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
@AllArgsConstructor
@NoArgsConstructor
@TableName("campus_sys_class")
public class ClassPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * 唯一自增 (使用雪花算法)
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 锁版本号 (乐观锁)
     * 插入时自动填充
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;

    /**
     * 是否启用
     * true/1-启用 false/0-禁用
     * 插入时自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Boolean enabled;

    /**
     * 年级 (年份当作年级)
     */
    private Integer grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private Integer classNumber;

}
