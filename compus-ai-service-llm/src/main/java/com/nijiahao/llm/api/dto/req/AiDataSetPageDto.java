package com.nijiahao.llm.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "AI知识库 - 分页查询入参")
public class AiDataSetPageDto implements Serializable {

    @Schema(description = "当前页", defaultValue = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页条数", defaultValue = "10")
    private Integer pageSize = 10;

    @Schema(description = "知识库名称")
    private String title;

}
