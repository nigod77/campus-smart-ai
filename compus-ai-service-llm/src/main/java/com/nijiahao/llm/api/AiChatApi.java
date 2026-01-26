package com.nijiahao.llm.api;

import com.nijiahao.llm.api.dto.req.AiReplayStreamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Tag(name = "02.模型交互接口" , description = "模型交互")
@RequestMapping("/llm/robotchat")
public interface AiChatApi {

    @Operation(summary = "流式返回内容" , description = "流式返回内容")
    @PostMapping("/replay/stream")
    Flux<String> replayStream(@RequestBody AiReplayStreamDto aiReplayStreamDto);
}
