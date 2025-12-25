package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.TermAddDto;
import com.nijiahao.system.api.dto.req.TermQueryDto;
import com.nijiahao.system.api.dto.req.TermUpdateDto;
import com.nijiahao.system.api.dto.res.TermVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/system/term")
@Tag(name = "04.学期管理接口" , description = "管理学期信息")
public interface TermManagementApi {

    @Operation(summary = "新增学期" , description = "新增一个学期")
    @PostMapping("/manage/add")
    Result<TermVo> addTerm(@RequestBody TermAddDto termAddDto);

    @Operation(summary = "修改学期" , description = "修改学期信息")
    @PostMapping("/manage/update")
    Result<TermVo> updateTerm(@RequestBody TermUpdateDto termUpdateDto);

    @Operation(summary = "删除一个学期" , description = "删除一个学期信息")
    @DeleteMapping("/manage/delete")
    Result<TermVo> deleteTerm(@RequestParam("id") Long id);

    @Operation(summary = "删除多个学期")
    @DeleteMapping("/manage/delete-all")
    Result<List<TermVo>> deleteAllTerm(List<Long> ids);

    @Operation(summary = "查询一个学期" , description = "根据id查询一个学习信息")
    @GetMapping("/manage/select")
    Result<TermVo> selectTerm(@RequestParam("id") Long id);

    @Operation(summary = "分页查询学期" , description = "分页查询学期信息")
    @GetMapping("/manage/query")
    PageResult<TermVo> queryTermPage(TermQueryDto termQueryDto);





}
