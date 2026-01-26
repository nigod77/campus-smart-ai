package com.nijiahao.llm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig2 {

    @Bean
    public ChatClient chatClient(@Qualifier("deepSeekChatModel") ChatModel chatmodel) {
        return ChatClient.builder(chatmodel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

}
