package com.nijiahao.llm.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "知识库展示对象")
public class AiDatasetVo {

    @Schema(description = "知识库ID")
    @JsonSerialize(using = ToStringSerializer.class) // 必加：防止前端Long类型精度丢失
    private Long id;

    @Schema(description = "知识库名称")
    private String name;

    @Schema(description = "简介")
    private String description;

    @Schema(description = "封面图标")
    private String avatar;

    @Schema(description = "创建者ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ownerId;

    // 如果有User模块，建议在Service层查出来填进去，前端不仅要ID还要名字
    @Schema(description = "创建者姓名")
    private String ownerName;

    @Schema(description = "可见性: 0-私有 1-公开")
    private Integer isPublic;

    @Schema(description = "可见性文案 (公开/私有)")
    private String isPublicText; // 后端直接转换好，方便前端直接显示

    @Schema(description = "包含文档数")
    private Integer docCount;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化日期
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化日期
    private LocalDateTime updateTime;



    private Integer revision;
}