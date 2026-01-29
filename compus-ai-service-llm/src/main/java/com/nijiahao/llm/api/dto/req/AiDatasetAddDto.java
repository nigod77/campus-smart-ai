package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "新建知识库请求参数")
public class AiDatasetAddDto {

    @Schema(description = "知识库名称 (必填)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "知识库名称不能为空")
    @Size(min = 2, max = 50, message = "名称长度需在2-50字之间")
    private String name;

    @Schema(description = "简介")
    @Size(max = 200, message = "简介不能超过200字")
    private String description;

    @Schema(description = "封面图标URL")
    private String avatar;

    @Schema(description = "可见性: 0-私有(默认) 1-公开")
    private Integer isPublic = 0; // 默认私有
}