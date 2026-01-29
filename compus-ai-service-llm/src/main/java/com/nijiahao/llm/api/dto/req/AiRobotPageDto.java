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
    private String robotName;

    private Integer isPublic;

    private Integer status;

    private String modelName;





}