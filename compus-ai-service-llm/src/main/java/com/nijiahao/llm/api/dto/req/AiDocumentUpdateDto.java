package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "修改文档信息请求参数")
public class AiDocumentUpdateDto {

    @Schema(description = "文档ID (必填)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文档ID不能为空")
    private Long id;

    @Schema(description = "显示的文件名 (仅修改展示名，不影响源文件)")
    @Size(min = 1, max = 100, message = "文件名长度需在1-100之间")
    private String filename;

    private Integer revision;

}