package com.nijiahao.llm.api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiReplayStreamDto {

    /**
     * sessionId
     */
    private Long sessionId;

    /**
     * 输入内容
     */
    String inputMessage;

    /**
     * 温度
     */
    Double temperature;
}
