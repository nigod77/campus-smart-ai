package com.nijiahao.llm.api.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class AiSessionDto {

    @JsonSerialize(using = ToStringSerializer.class) // 必加：防止前端Long类型精度丢失
    private Long robotId;
}
