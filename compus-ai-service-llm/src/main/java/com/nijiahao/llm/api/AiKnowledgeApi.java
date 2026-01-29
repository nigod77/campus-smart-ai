package com.nijiahao.llm.api;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.dto.req.AiDatasetAddDto;
import com.nijiahao.llm.api.dto.req.AiDatasetUpdateDto;
import com.nijiahao.llm.api.dto.req.AiDocumentUpdateDto;
import com.nijiahao.llm.api.dto.res.AiDatasetVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@Tag(name = "04.知识库管理接口")
@RequestMapping("/llm/knowledge")
public interface AiKnowledgeApi {

    @Operation(summary = "知识库添加" , description = "知识库添加")
    @PostMapping("/manage/add")
    Result<AiDatasetVo> addKnowledge(@RequestBody AiDatasetAddDto aiDatasetAddDto);

    @Operation(summary = "知识库修改" , description = "知识库修改")
    @PostMapping("/manage/update")
    Result<AiDatasetVo> updateKnowledge(@RequestBody AiDatasetUpdateDto aiDatasetUpdateDto);

    @Operation(summary = "根据id查询一个知识库" , description = "查询一个知识库")
    @GetMapping("/manage/getbyid")
    Result<AiDatasetVo> getKnowledge(@RequestParam("id") Integer id);

    @Operation(summary = "分页查询知识库" , description = "分页查询知识库")
    @PostMapping("/manage/query")
    Result<AiDatasetVo> queryKnowledge();

    @Operation(summary = "删除一个知识库" , description = "删除一个知识库")
    @DeleteMapping("/manage/delete")
    Result<AiDatasetVo> deleteKnowledge(@RequestParam("id") Integer id);



}
