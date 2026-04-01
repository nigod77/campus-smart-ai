package com.nijiahao.llm.controller;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.AiDocumentApi;
import com.nijiahao.llm.service.AiDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AiDocumentController implements AiDocumentApi {

    @Autowired
    private AiDocumentService aiDocumentService;

    @Override
    public Result<Long> documentAdd(MultipartFile file, Long dataSetId) {
        Long l = aiDocumentService.documentAdd(file, dataSetId);
        return Result.success(l);
    }
}
