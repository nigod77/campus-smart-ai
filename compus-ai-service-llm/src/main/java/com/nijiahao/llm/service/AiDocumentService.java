package com.nijiahao.llm.service;

import org.springframework.web.multipart.MultipartFile;

public interface AiDocumentService {

    Long documentAdd(MultipartFile file, Long dataSetId);

}
