package com.nijiahao.llm.api;

import com.nijiahao.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "05.知识文档接口" , description = "知识文档crud")
@RequestMapping("/llm/documents")
public interface AiDocumentApi {

    @Operation(summary = "添加一个知识文档")
    @PostMapping("/manage/add")
    Result<Long> documentAdd(@RequestParam("file") MultipartFile file , @RequestParam("dataSetId") Long dataSetId);
}
