package com.nijiahao.llm.rabitmqConsumer;

import com.nijiahao.llm.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleDocParse(Long docId) {
        log.info("============== [消费者] 收到任务 ==============");
        log.info("正在处理文档 ID: {}", docId);

        try {
            // 模拟业务耗时：下载文件
            log.info("1. 从阿里云 OSS 下载文件...");
            Thread.sleep(1000);

            // 模拟业务耗时：AI 解析
            log.info("2. 调用 AI 模型进行切片和 Embedding...");
            Thread.sleep(2000);

            // 模拟业务耗时：存 Milvus
            log.info("3. 存入 Milvus 向量库...");
            Thread.sleep(500);

            log.info("✅ 文档 {} 处理完成，状态已更新为 [成功]", docId);

        } catch (InterruptedException e) {
            log.error("❌ 处理失败", e);
        }
        log.info("============== [消费者] 任务结束 ==============");
    }
}
