package com.nijiahao.llm.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "知识库文档展示对象")
public class AiDocumentVo {

    @Schema(description = "文档ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "所属知识库ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long datasetId;

    @Schema(description = "文件名")
    private String filename;

    @Schema(description = "文件类型后缀")
    private String fileExt;

    @Schema(description = "文件下载/预览地址")
    private String fileUrl;

    @Schema(description = "文件大小 (字节)")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileSize;

    @Schema(description = "可读的文件大小 (如 1.2MB, 500KB)")
    private String fileSizeReadable; // Service层转换或前端转换均可，建议后端做

    @Schema(description = "状态: 0-待解析 1-解析中 2-向量化中 3-成功 9-失败")
    private Integer status;

    @Schema(description = "状态文案 (解析中/已完成/失败)")
    private String statusText;

    @Schema(description = "状态颜色 (success/processing/error)")
    private String statusColor; // 可选：给前端UI组件用的颜色状态

    @Schema(description = "失败原因")
    private String errorMsg;

    @Schema(description = "切片数量")
    private Integer chunkCount;

    @Schema(description = "消耗Token")
    private Integer tokenCount;

    @Schema(description = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}