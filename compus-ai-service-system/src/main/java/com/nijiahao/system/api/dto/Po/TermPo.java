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
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("campus_sys_term")
public class TermPo extends BaseEntity implements Serializable {
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
     * 学期名
     * 例如：2023-2024学年 第一学期
     */
    private String termName;

    /**
     * 学期开始日期 (对应数据库 DATE 类型)
     */
    private LocalDate termStart;

    /**
     * 学期结束日期 (对应数据库 DATE 类型)
     */
    private LocalDate termEnd;

    /**
     * 是否是本学期
     * true/1-是 false/0-否
     */
    private Boolean isCurrent;

}
