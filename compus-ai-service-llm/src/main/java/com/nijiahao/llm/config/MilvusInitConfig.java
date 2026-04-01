package com.nijiahao.llm.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class MilvusInitConfig {

    @Bean
    public CommandLineRunner initMilvus(VectorStore vectorStore) {
        return args -> {

            System.out.println("👉 开始向 Milvus 写入初始化数据");

            Document d1 = new Document(
                    "Spring AI 是一个用于构建 AI 应用的 Spring 项目",
                    Map.of("content", "Spring AI 是一个用于构建 AI 应用的 Spring 项目")
            );


            Document d2 = new Document(
                    "Milvus 是一个高性能向量数据库",
                    Map.of("content", "Milvus 是一个高性能向量数据库")
            );


            Document d3 = new Document(
                    "Ollama 可以在本地运行大模型和 embedding",
                    Map.of("content", "Ollama 可以在本地运行大模型和 embedding")
            );

            //vectorStore.add(List.of(d1, d2, d3));

            System.out.println("✅ Milvus 初始化入库完成");
        };
    }

}