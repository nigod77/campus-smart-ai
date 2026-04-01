package com.nijiahao.llm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.common.core.utils.AliyunOssUtils;
import com.nijiahao.llm.api.dto.po.AiDatasetPo;
import com.nijiahao.llm.api.dto.po.AiDocumentPo;
import com.nijiahao.llm.mapper.AiDatasetMapper;
import com.nijiahao.llm.mapper.AiDocumentMapper;
import com.nijiahao.llm.service.AiDocumentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.nijiahao.llm.config.RabbitConfig;

import java.util.UUID;

@Service
public class AiDocumentServiceImpl extends ServiceImpl<AiDocumentMapper , AiDocumentPo> implements AiDocumentService {

    @Autowired
    private AiDocumentMapper aiDocumentMapper;
    @Autowired
    private AiDatasetMapper aiDatasetMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 建议在外部统一定义这些状态枚举，这里为了方便展示先写成常量
    private static final Integer STATUS_PENDING = 0; // 等待处理/排队中
    private static final Integer STATUS_FAILED = 9;  // 处理失败


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long documentAdd(MultipartFile file, Long dataSetId) {

        if (file.isEmpty() || dataSetId == null) {
            throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR , "【documentAdd】文件和dataSetId参数不能为空");
        }

        //查出这个知识库
        AiDatasetPo aiDatasetPo = aiDatasetMapper.selectById(dataSetId);
        if (aiDatasetPo == null) {
            throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR , "没有这条知识库数据");
        }

        String originalFilename = file.getOriginalFilename();

        // 2. 安全地获取文件后缀并校验（修复无后缀名抛异常的bug）
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 可选：在这里加上文件类型的白名单校验，防止上传 .exe, .sh 等危险或无法解析的文件
        // if (!List.of(".txt", ".pdf", ".docx").contains(suffix.toLowerCase())) {
        //     throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR, "不支持的文件格式");
        // }

        String objectName = "uploads/documents/" + UUID.randomUUID() + suffix;
        String url;
        // 3. 将文件上传阿里云OSS
        try {
            url = AliyunOssUtils.uploadStream(objectName, file.getInputStream());
        } catch (Exception e) {
            throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR, "文件上传OSS失败: " + e.getMessage());
        }

        //第一次入mysql
        // 4. 构建并入库文档信息 (初始化状态)
        AiDocumentPo doc = new AiDocumentPo();
        doc.setDatasetId(dataSetId);
        doc.setFilename(originalFilename);
        doc.setFileUrl(url);
        doc.setFileKey(objectName);
        doc.setFileSize(file.getSize());
        doc.setFileExt(suffix);

        doc.setStatus(STATUS_PENDING); // 规范化状态码：0 排队中
        doc.setChunkCount(0);

        int rows = aiDocumentMapper.insert(doc);
        if (rows <= 0) {
            throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR, "文档信息保存到数据库失败");
        }
        // 5. 通知消息队列进行后续解析和向量化入库
        try {
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,   // 交换机
                    RabbitConfig.ROUTING_KEY,     // 路由键
                    doc.getId()                   // 发送记录ID给消费者
            );
        } catch (Exception e) {
            // 如果发 MQ 异常，因为加了事务，正常抛出异常整个方法会回滚（包括前面 insert 的数据）。
            // 但如果业务上希望保留上传记录，仅标记为失败，你需要像原来一样手动 catch，并将异常往外抛出或仅做日志记录。
            // 结合你原本的思路，我们保留记录，更新为失败状态。
            doc.setStatus(STATUS_FAILED);
            doc.setErrorMsg("MQ消息发送失败: " + e.getMessage());
            aiDocumentMapper.updateById(doc);

            // 注意：因为我们手动 catch 并处理了，所以不需要往外抛异常，否则会触发 @Transactional 的回滚。
            // throw new ServiceException(ResultCode.DOCUMENT_MANAGE_ERROR, "上传成功但解析任务提交失败");
        }


        // 6. 返回生成的文档主键 ID，方便前端拿这个 ID 去轮询处理进度
        return doc.getId();

    }
}
