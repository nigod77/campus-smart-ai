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


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSuggestedQuestionVo implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class) private Long id;
    @JsonSerialize(using = ToStringSerializer.class) private Long robotId;
    private String question;
    private Integer sort;
    private Integer revision; // Added
}