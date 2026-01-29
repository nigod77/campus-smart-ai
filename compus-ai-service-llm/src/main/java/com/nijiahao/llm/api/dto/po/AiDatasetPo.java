package com.nijiahao.llm.api.dto.po;

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

/**
 * 知识库 PO
 * 对应表: campus_ai_dataset
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("campus_ai_dataset")
public class AiDatasetPo extends BaseEntity implements Serializable {
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
     * 知识库名称
     */
    private String name;

    /**
     * 简介/描述
     */
    private String description;

    /**
     * 封面图标
     */
    private String avatar;

    /**
     * 所属用户ID
     * (外键也建议序列化成String，防止前端精度丢失)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ownerId;

    /**
     * 是否公开
     * 1-公开 0-私有
     */
    private Integer isPublic;

    /**
     * 包含文档数
     * (冗余字段，用于列表快速展示)
     */
    private Integer docCount;


}