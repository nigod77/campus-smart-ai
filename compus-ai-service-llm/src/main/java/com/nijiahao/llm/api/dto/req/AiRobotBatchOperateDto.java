package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "AI机器人 - 批量操作入参")
public class AiRobotBatchOperateDto implements Serializable {

    @Schema(description = "ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "ID列表不能为空")
    private List<Long> ids;

    @Schema(description = "目标状态 (仅在更新状态时使用)")
    private Boolean targetStatus;
}