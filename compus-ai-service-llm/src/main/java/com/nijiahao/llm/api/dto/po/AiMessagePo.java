package com.nijiahao.llm.api.dto.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nijiahao.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "campus_ai_message", autoResultMap = true)
public class AiMessagePo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long sessionId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String role;
    private String content;
    private Integer type;
    private Integer tokenInput;
    private Integer tokenOutput;
    private Integer status;
    private String errorMsg;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> citations;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;
}