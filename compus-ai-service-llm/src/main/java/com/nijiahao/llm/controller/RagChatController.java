package com.nijiahao.llm.controller;

import com.nijiahao.llm.service.RagChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/llm/session")
public class RagChatController {

    private final RagChatService ragChatService;

    public RagChatController(RagChatService ragChatService) {
        this.ragChatService = ragChatService;
    }

    /**
     * 示例：
     * POST /rag/chat
     * {
     *   "question": "什么是向量数据库？"
     * }
     */
    @PostMapping("/chat")
    public String chat(@RequestBody RagChatRequest request) {
        return ragChatService.chat(request.getQuestion());
    }

    // 简单 DTO
    public static class RagChatRequest {
        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }
}
