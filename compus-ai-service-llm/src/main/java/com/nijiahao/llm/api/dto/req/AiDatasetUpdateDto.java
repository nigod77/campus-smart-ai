package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull; // Spring Boot 3.x 使用 jakarta, 2.x 使用 javax
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "修改知识库请求参数")
public class AiDatasetUpdateDto {

    @Schema(description = "知识库ID (必填)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "知识库ID不能为空")
    private Long id;

    @Schema(description = "知识库名称")
    @Size(min = 2, max = 50, message = "名称长度需在2-50字之间")
    private String name;

    @Schema(description = "简介")
    @Size(max = 200, message = "简介不能超过200字")
    private String description;

    @Schema(description = "封面图标URL")
    private String avatar;

    @Schema(description = "可见性: 0-私有 1-公开")
    private Integer isPublic;

    @Schema(description = "乐观锁")
    private Integer revision;
}