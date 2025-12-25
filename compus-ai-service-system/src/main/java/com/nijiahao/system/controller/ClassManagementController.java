package com.nijiahao.system.controller;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.ClassManagementApi;
import com.nijiahao.system.api.dto.req.ClassAddDto;
import com.nijiahao.system.api.dto.req.ClassQueryDto;
import com.nijiahao.system.api.dto.req.ClassUpdateDto;
import com.nijiahao.system.api.dto.res.ClassVo;
import com.nijiahao.system.service.ClassManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassManagementController implements ClassManagementApi {

    @Autowired
    private ClassManagementService classManagementService;

    @Override
    public Result<ClassVo> addClass(@RequestBody ClassAddDto classAddDto) {
        ClassVo classVo = classManagementService.addClass(classAddDto);
        return Result.success(classVo);
    }

    @Override
    public Result<ClassVo> updateClass(ClassUpdateDto classUpdateDto) {
        ClassVo classVo = classManagementService.updateClass(classUpdateDto);
        return Result.success(classVo);
    }

    @Override
    public Result<ClassVo> getClassById(@RequestParam("classId") Long id) {
        ClassVo classVo = classManagementService.selectById(id);
        return Result.success(classVo);
    }

    @Override
    public Result<ClassVo> deleteClassById(@RequestParam("classId") Long classId) {
        ClassVo classVo = classManagementService.delete(classId);
        return Result.success(classVo);
    }

    @Override
    public Result<List<ClassVo>> deleteClassByIds(@RequestBody List<Long> classIds) {
        List<ClassVo> classVoList = classManagementService.deleteAll(classIds);
        return Result.success(classVoList);
    }

    @Override
    public PageResult<ClassVo> queryUser(ClassQueryDto classQueryDto) {
        return classManagementService.pageQuery(classQueryDto);
    }

}
