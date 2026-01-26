package com.nijiahao.llm.api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * 专门用于消息落库的参数对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiMessageLogDto {

    private Long sessionId;

    private Long userId;     // 如果是AI回复，此处可为0或null

    private String role;     // user / assistant / system

    private String content;  // 消息内容
    
    @Builder.Default
    private Integer type = 1; // 默认为文本
    
    @Builder.Default
    private Integer status = 1; // 默认为成功
    
    private String errorMsg;  // 失败原因
    
    private Integer tokenInput;

    private Integer tokenOutput;
    
    // RAG 引用来源
    private List<Map<String, Object>> citations;
}