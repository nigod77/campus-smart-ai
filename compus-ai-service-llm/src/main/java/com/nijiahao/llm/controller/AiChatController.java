package com.nijiahao.llm.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nijiahao.llm.api.AiChatApi;
import com.nijiahao.llm.api.dto.req.AiReplayStreamDto;
import com.nijiahao.llm.service.AiChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AiChatController implements AiChatApi {

    @Autowired
    private AiChatService aiChatService;




    @Override
    public Flux<String> replayStream(AiReplayStreamDto aiReplayStreamDto) {
        Long currentUserId = StpUtil.getLoginIdAsLong();
        return aiChatService.llmStreamReplay(aiReplayStreamDto , currentUserId);
    }
}
