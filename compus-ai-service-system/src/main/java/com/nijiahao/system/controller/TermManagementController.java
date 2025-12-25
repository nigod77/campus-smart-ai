package com.nijiahao.system.controller;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.TermManagementApi;
import com.nijiahao.system.api.dto.req.TermAddDto;
import com.nijiahao.system.api.dto.req.TermQueryDto;
import com.nijiahao.system.api.dto.req.TermUpdateDto;
import com.nijiahao.system.api.dto.res.TermVo;
import com.nijiahao.system.service.TermManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TermManagementController implements TermManagementApi {

    @Autowired
    private TermManagementService termManagementService;

    @Override
    public Result<TermVo> addTerm(TermAddDto termAddDto) {
        TermVo termVo = termManagementService.addTerm(termAddDto);
        return Result.success(termVo);
    }

    @Override
    public Result<TermVo> updateTerm(TermUpdateDto termUpdateDto) {
        TermVo termVo = termManagementService.update(termUpdateDto);
        return Result.success(termVo);
    }

    @Override
    public Result<TermVo> deleteTerm(Long id) {
        TermVo termVo = termManagementService.deleteTerm(id);
        return Result.success(termVo);
    }

    @Override
    public Result<List<TermVo>> deleteAllTerm(List<Long> ids) {
        List<TermVo> termVos = termManagementService.deleteTermAll(ids);
        return Result.success(termVos);
    }

    @Override
    public Result<TermVo> selectTerm(Long id) {
        TermVo termVo = termManagementService.selectTerm(id);
        return Result.success(termVo);
    }

    @Override
    public PageResult<TermVo> queryTermPage(TermQueryDto termQueryDto) {
        return termManagementService.terQueryPage(termQueryDto);
    }
}
