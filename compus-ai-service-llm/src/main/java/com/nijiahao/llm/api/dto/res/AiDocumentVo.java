package com.nijiahao.llm.api.dto.res;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiDocumentVo implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class) private Long id;
    @JsonSerialize(using = ToStringSerializer.class) private Long datasetId;
    private String filename;
    private String fileUrl;
    private Long fileSize;
    private Integer status;
    private String errorMsg;
    private Integer chunkCount;
    private Integer revision; // Added
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createTime;
}