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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("campus_ai_document")
public class AiDocumentPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long datasetId;

    private String filename;
    private String fileUrl;
    private Long fileSize;
    private Integer status;
    private String errorMsg;
    private String vectorCollection;
    private Integer chunkCount;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;
}