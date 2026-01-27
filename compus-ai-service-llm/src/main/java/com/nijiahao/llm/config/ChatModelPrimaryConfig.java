package com.nijiahao.llm.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatModelPrimaryConfig {

    @Bean
    @Primary
    public ChatModel primaryChatModel(
            @Qualifier("openAiChatModel") ChatModel chatModel
    ) {
        return chatModel;
    }
}
