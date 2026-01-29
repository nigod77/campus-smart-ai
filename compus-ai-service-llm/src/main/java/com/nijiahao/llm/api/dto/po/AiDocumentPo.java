package com.nijiahao.ai.api.dto.Po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nijiahao.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 知识库文档 PO
 * 对应表: campus_ai_document
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("campus_ai_document")
public class AiDocumentPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 归属知识库ID
     * (外键也建议序列化成String)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long datasetId;

    /**
     * 锁版本号 (乐观锁)
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;

    /**
     * 原始文件名
     */
    private String filename;

    /**
     * 扩展名 (如 .pdf, .docx)
     */
    private String fileExt;

    /**
     * 阿里云OSS访问地址
     */
    private String fileUrl;

    /**
     * 阿里云OSS ObjectKey (用于删除和下载)
     */
    private String fileKey;

    /**
     * 文件Hash (MD5) 防止重复上传
     */
    private String fileHash;

    /**
     * 文件大小 (字节)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileSize;

    /**
     * 状态机
     * 0-待解析 1-解析中 2-向量化中 3-成功 9-失败
     */
    private Integer status;

    /**
     * 失败日志/错误信息
     */
    private String errorMsg;

    /**
     * 切片数量
     */
    private Integer chunkCount;

    /**
     * 消耗Token数 (预估)
     */
    private Integer tokenCount;

}