package com.nijiahao.llm.service;

import com.nijiahao.llm.api.dto.req.AiDatasetAddDto;
import com.nijiahao.llm.api.dto.req.AiDatasetUpdateDto;
import com.nijiahao.llm.api.dto.res.AiDatasetVo;

public interface AiKnowledgeService {

    AiDatasetVo addDataset(AiDatasetAddDto aiDatasetAddDto);

    AiDatasetVo updateDataset(AiDatasetUpdateDto aiDatasetUpdateDto);

    AiDatasetVo getDatasetById(Integer id);

    AiDatasetVo deleteKnowledge(Integer id);
}
