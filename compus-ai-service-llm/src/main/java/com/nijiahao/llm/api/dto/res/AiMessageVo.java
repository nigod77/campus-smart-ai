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
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiMessageVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class) private Long id;
    @JsonSerialize(using = ToStringSerializer.class) private Long sessionId;
    @JsonSerialize(using = ToStringSerializer.class) private Long userId;
    private String role;
    private String content;
    private Integer type;
    private Integer status;
    private String errorMsg;
    private List<Map<String, Object>> citations;
    private Integer tokenInput;
    private Integer tokenOutput;
    private Integer revision; // Added
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createTime;
}