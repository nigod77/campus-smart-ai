package com.nijiahao.llm.controller;

import com.nijiahao.llm.service.impl.MilvusWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/llm/session")
public class MilvusController {

    private final MilvusWriteService milvusWriteService;

    public MilvusController(MilvusWriteService milvusWriteService) {
        this.milvusWriteService = milvusWriteService;
    }

    /**
     * 写入一条
     */
    @PostMapping("/save")
    public String save(@RequestBody String text) {
        milvusWriteService.saveText(text);
        return "OK";
    }

    /**
     * 批量写入
     */
    @PostMapping("/save-batch")
    public String saveBatch(@RequestBody List<String> texts) {
        milvusWriteService.saveBatch(texts);
        return "OK";
    }
}
