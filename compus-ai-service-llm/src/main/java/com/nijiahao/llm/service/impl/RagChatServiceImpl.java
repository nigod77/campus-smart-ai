package com.nijiahao.llm.service.impl;

import com.nijiahao.llm.service.RagChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagChatServiceImpl implements RagChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagChatServiceImpl(
            ChatClient chatClient,
            VectorStore vectorStore
    ) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @Override
    public String chat(String question) {

        // 1️⃣ 向量检索（TopK=4）
        List<Document> documents = vectorStore.similaritySearch(question);

        if (documents.isEmpty()) {
            return "没有检索到相关知识，请换个问题试试。";
        }

        // 2️⃣ 拼接上下文
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));

        // 3️⃣ RAG Prompt
        String prompt = """
                你是一个专业、严谨的助手。
                请**只根据下面提供的资料回答问题**，不要编造。

                【资料】
                %s

                【问题】
                %s
                """.formatted(context, question);

        // 4️⃣ 调用大模型
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
