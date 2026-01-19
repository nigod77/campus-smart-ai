package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "AI机器人 - 修改入参")
public class AiRobotEditDto extends AiRobotAddDto {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "乐观锁版本号 (必传)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号不能为空，请刷新数据后重试")
    private Integer revision;
}