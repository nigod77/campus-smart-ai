package com.nijiahao.llm.controller;

import com.nijiahao.common.core.utils.AliyunOssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/llm/oss")
@Slf4j
public class TestAliyunOssController {

    @GetMapping("/list")
    public String getAllFiles() {
        List<String> strings = AliyunOssUtils.listFileNames();
        return strings.toString();
    }

    @GetMapping("/getfile")
    public String getFile(@RequestParam("filepath") String filepath) {
        return AliyunOssUtils.getFileContentAsString(filepath);
    }

    /**
     * 上传文件接口
     * 请求方式: POST
     * 参数: file (form-data)
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // 1. 检查文件是否为空
        if (file.isEmpty()) {
            return "上传失败：文件不能为空";
        }

        try {
            // 2. 获取文件的原始名称 (例如: "resume.docx")
            String originalFilename = file.getOriginalFilename();

            // 3. 构建 OSS 中的存储路径 (ObjectName)
            // 建议1：直接用原名 (简单，但如果两个人传了同名文件会覆盖)
            // String objectName = "uploads/" + originalFilename;

            // 建议2：使用 UUID 重命名防止覆盖 (生产环境推荐)
            String suffix = null;
            if (originalFilename != null) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String objectName = "uploads/" + UUID.randomUUID() + suffix;

            // 4. 调用工具类上传 (注意：这里直接获取 InputStream 传进去)
            String url = AliyunOssUtils.uploadStream(objectName, file.getInputStream());

            return "上传成功！访问地址: " + url;

        } catch (IOException e) {
            log.error("文件上传发生异常", e);
            return "上传失败：" + e.getMessage();
        }
    }
}
