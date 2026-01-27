package com.nijiahao.llm.service.impl;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MilvusWriteService {

    private final VectorStore vectorStore;

    public MilvusWriteService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * 手动写入一条文本
     */
    public void saveText(String text) {
        Document document = new Document(
                text,
                Map.of(
                        "source", "manual",
                        "type", "text"
                )
        );

        vectorStore.add(List.of(document));
    }

    /**
     * 批量写入
     */
    public void saveBatch(List<String> texts) {
        List<Document> docs = texts.stream()
                .map(t -> new Document(
                        t,
                        Map.of("source", "batch")
                ))
                .toList();

        vectorStore.add(docs);
    }
}
