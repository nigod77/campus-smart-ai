package com.nijiahao.llm.rabitmqConsumer;

import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.common.core.utils.AliyunOssUtils;
import com.nijiahao.llm.api.dto.po.AiDocumentPo;
import com.nijiahao.llm.config.RabbitConfig;
import com.nijiahao.llm.mapper.AiDatasetMapper;
import com.nijiahao.llm.mapper.AiDocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DocConsumer {

    @Autowired
    private AiDocumentMapper aiDocumentMapper;

    @Autowired
    private AiDatasetMapper aiDatasetMapper;

    @Autowired
    private VectorStore vectorStore;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleDocParse(Long docId) {
        log.info("============== [消费者] 收到任务 ==============");
        log.info("正在处理文档 ID: {}", docId);

        AiDocumentPo aiDocumentPo = aiDocumentMapper.selectById(docId);
        if (aiDocumentPo == null) {
            log.error("未找到文档记录，ID: {}", docId);
            return; // 消费者找不到记录直接 return，不再抛异常，避免 MQ 无限重试
        }

        try {
            // 1. 更新状态为：解析中
            aiDocumentPo.setStatus(1);
            aiDocumentMapper.updateById(aiDocumentPo);
            log.info("状态更新为: 解析中");

            // 2. 获取字节数组 (修复 Bug：必须用 fileKey 获取 OSS 文件)
            byte[] fileBytes = AliyunOssUtils.getFileBytes(aiDocumentPo.getFileKey());
            if (fileBytes == null) {
                throw new RuntimeException("文件从 OSS 下载失败，可能文件不存在");
            }

            // 3. 包装成 Spring AI 需要的 Resource，并提供原始文件名给 Tika 做后缀判断
            ByteArrayResource resource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return aiDocumentPo.getFilename();
                }
            };

            // 4. 使用 Tika 解析器
            TikaDocumentReader reader = new TikaDocumentReader(resource);
            List<Document> documents = reader.get();

            // 5. 切片处理
            TokenTextSplitter splitter = new TokenTextSplitter(800, 100, 5, 10000, true);
            List<Document> chunks = splitter.apply(documents);

            // 6. 补充元数据 (添加了 datasetId 方便后续隔离检索)
            for (Document chunk : chunks) {
                chunk.getMetadata().put("docId", docId);
                chunk.getMetadata().put("datasetId", aiDocumentPo.getDatasetId());
            }

            // 7. 写入向量库
            vectorStore.add(chunks); // 建议用 add()，这是 Spring AI VectorStore 更标准的写入方法

            // 8. 成功完成，更新状态和分块数
            aiDocumentPo.setStatus(2); // 假设 2 是完成状态
            aiDocumentPo.setChunkCount(chunks.size());
            aiDocumentMapper.updateById(aiDocumentPo);
            log.info("============== [消费者] 任务成功结束，共切分 {} 块 ==============", chunks.size());

        } catch (Exception e) {
            log.error("解析文档失败 ID: {}", docId, e);
            // 9. 发生异常，记录错误信息，将状态标记为失败，防止卡在“解析中”
            aiDocumentPo.setStatus(9); // 假设 9 是失败状态
            aiDocumentPo.setErrorMsg(e.getMessage() != null ? e.getMessage() : "未知异常");
            aiDocumentMapper.updateById(aiDocumentPo);
            log.info("============== [消费者] 任务失败结束 ==============");
        }
    }
}