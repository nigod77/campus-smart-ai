package com.nijiahao.llm.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OllamaEmbeddingCheck implements CommandLineRunner {

    private final EmbeddingModel embeddingModel;

    public OllamaEmbeddingCheck(
            @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public void run(String... args) {
        var embedding = embeddingModel.embed("hello ollama");
        System.out.println(embedding);
    }
}