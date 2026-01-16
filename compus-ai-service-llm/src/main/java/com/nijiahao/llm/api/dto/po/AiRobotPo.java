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
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "campus_ai_robot", autoResultMap = true)
public class AiRobotPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String robotName;
    private String avatar;
    private String description;
    private String welcomeMessage;
    private String modelName;
    private String systemPrompt;
    private Double temperature;
    private Double topP;
    private Integer historyLimit;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> knowledgeConfig;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> pluginConfig;

    private Boolean isPublic;
    private Boolean status;

    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;
}