package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "AI机器人 - 分页查询入参")
public class AiRobotPageDto implements Serializable {

    @Schema(description = "当前页", defaultValue = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页条数", defaultValue = "10")
    private Integer pageSize = 10;

    // --- 过滤条件 ---

    @Schema(description = "机器人名称 (模糊查询)")
    private String robotName;

    @Schema(description = "模型标识 (精确匹配)")
    private String modelName;

    @Schema(description = "状态 (0-禁用 1-启用)")
    private Boolean status;

    @Schema(description = "是否公开")
    private Boolean isPublic;
}