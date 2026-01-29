package com.nijiahao.llm.controller;


import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.AiKnowledgeApi;
import com.nijiahao.llm.api.dto.req.AiDatasetAddDto;
import com.nijiahao.llm.api.dto.req.AiDatasetUpdateDto;
import com.nijiahao.llm.api.dto.res.AiDatasetVo;
import com.nijiahao.llm.service.AiKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiKnowledgeController implements AiKnowledgeApi {

    @Autowired
    private AiKnowledgeService aiKnowledgeService;

    @Override
    public Result<AiDatasetVo> addKnowledge(AiDatasetAddDto aiDatasetAddDto) {
        return Result.success(aiKnowledgeService.addDataset(aiDatasetAddDto));
    }

    @Override
    public Result<AiDatasetVo> updateKnowledge(AiDatasetUpdateDto aiDatasetUpdateDto) {
        return Result.success(aiKnowledgeService.updateDataset(aiDatasetUpdateDto));
    }

    @Override
    public Result<AiDatasetVo> getKnowledge(Integer id) {
        return Result.success(aiKnowledgeService.getDatasetById(id));
    }

    @Override
    public Result<AiDatasetVo> queryKnowledge() {
        return null;
    }

    @Override
    public Result<AiDatasetVo> deleteKnowledge(Integer id) {
        return Result.success(aiKnowledgeService.deleteKnowledge(id));
    }
}
