package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Schema(description = "AI机器人 - 新增入参")
public class AiRobotAddDto implements Serializable {

    @Schema(description = "机器人名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "机器人名称不能为空")
    @Size(max = 50, message = "名称长度不能超过50个字符")
    private String robotName;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "功能描述")
    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    @Schema(description = "开场白")
    private String welcomeMessage;

    @Schema(description = "模型标识 (如: deepseek-r1)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模型标识不能为空")
    private String modelName;

    @Schema(description = "系统提示词")
    private String systemPrompt;

    @Schema(description = "温度 (0.0-1.0)", defaultValue = "0.7")
    @DecimalMin(value = "0.0", message = "温度不能小于0")
    @DecimalMax(value = "1.0", message = "温度不能大于1")
    private Double temperature = 0.7;

    @Schema(description = "核采样 (0.0-1.0)", defaultValue = "0.9")
    @DecimalMin(value = "0.0", message = "核采样不能小于0")
    @DecimalMax(value = "1.0", message = "核采样不能大于1")
    private Double topP = 0.9;
    
    @Schema(description = "历史对话保留条数", defaultValue = "10")
    @Min(value = 0, message = "历史条数不能为负")
    @Max(value = 50, message = "历史条数建议不超过50")
    private Integer historyLimit = 10;

    @Schema(description = "RAG知识库配置 (JSON结构)")
    private Map<String, Object> knowledgeConfig;

    @Schema(description = "插件/MCP配置 (JSON结构)")
    private Map<String, Object> pluginConfig;

    @Schema(description = "是否公开", defaultValue = "false")
    private Boolean isPublic = false;

    @Schema(description = "状态: true-启用 false-禁用", defaultValue = "true")
    private Boolean status = true;
}