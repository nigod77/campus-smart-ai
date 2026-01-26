package com.nijiahao.llm.api.dto.req;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class AiSessionUpdateDto {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long SessionId;

    private String title;

    private Boolean isPinned;

    private Integer revision;

}
