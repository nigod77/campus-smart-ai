package com.nijiahao.llm.service;

import com.nijiahao.llm.api.dto.po.AiMessagePo;
import com.nijiahao.llm.api.dto.req.AiMessageLogDto;
import com.nijiahao.llm.api.dto.req.AiReplayStreamDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AiChatService {

    //创建会话
    Long createSession(Long robotId , Long userId);

    Long saveMessage(AiMessageLogDto logDto);

    //获取历史记录(上下文)
    List<AiMessagePo> getHistoryMsg(Long sessionId , int limit , Long userId);

    Flux<String> llmStreamReplay(AiReplayStreamDto aiReplayStreamDto , Long userId);
}
