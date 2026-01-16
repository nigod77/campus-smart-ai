package com.nijiahao.llm.api.dto.res;
// imports omitted for brevity... (同上)
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiRobotVo implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class) private Long id;
    private String robotName;
    private String avatar;
    private String description;
    private String welcomeMessage;
    private String modelName;
    private String systemPrompt;
    private Double temperature;
    private Double topP;
    private Integer historyLimit;
    private Map<String, Object> knowledgeConfig;
    private Map<String, Object> pluginConfig;
    private Boolean isPublic;
    private Boolean status;
    private Integer revision; // Added
    @JsonSerialize(using = ToStringSerializer.class) private Long createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime updateTime;
}