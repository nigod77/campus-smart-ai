package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.ClassAddDto;
import com.nijiahao.system.api.dto.req.ClassQueryDto;
import com.nijiahao.system.api.dto.req.ClassUpdateDto;
import com.nijiahao.system.api.dto.res.ClassVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "03.班级管理模块" , description = "班级的crud")
@RequestMapping("/system/class")
public interface ClassManagementApi {


    @Operation(summary = "新增班级" , description = "输入 年级（年份），专业 ，班级 存入数据库")
    @PostMapping("/manage/add")
    Result<ClassVo> addClass(@RequestBody ClassAddDto classAddDto) ;

    @Operation(summary = "修改班级" , description = "修改班级")
    @PostMapping("/manage/update")
    Result<ClassVo> updateClass(@RequestBody ClassUpdateDto classUpdateDto) ;

    @Operation(summary = "根据id查班级"  , description = "根据id查找班级")
    @GetMapping("/manage/select")
    Result<ClassVo> getClassById(@RequestParam("classId") Long id);

    @Operation(summary = "删除一个班级" , description = "删除一个班级")
    @DeleteMapping("/manage/delete")
    Result<ClassVo> deleteClassById(@RequestParam("classId") Long classId);

    @Operation(summary = "删除多个班级" , description = "删除多个班级")
    @DeleteMapping("/manage/delete-all")
    Result<List<ClassVo>> deleteClassByIds(@RequestBody List<Long> classIds);

    @Operation(summary = "分页查询班级" , description = "分页查询班级")
    @GetMapping("/manage/query")
    PageResult<ClassVo> queryUser(ClassQueryDto classQueryDto);

}
