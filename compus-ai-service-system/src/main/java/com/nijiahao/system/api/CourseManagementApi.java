package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.CourseAddDto;
import com.nijiahao.system.api.dto.req.CourseQueryDto;
import com.nijiahao.system.api.dto.req.CourseUpdateDto;
import com.nijiahao.system.api.dto.res.CourseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/system/course")
@Tag(name = "05.课程管理" , description = "课程的curd")
public interface CourseManagementApi {

    @Operation(summary = "新增课程" , description = "新增一个课程")
    @PostMapping("/manage/add")
    Result<CourseVo> addCourse(@RequestBody CourseAddDto courseAddDto);

    @Operation(summary = "修改课程" , description = "修改一个课程的信息")
    @PostMapping("/manage/update")
    Result<CourseVo> updateCourse(@RequestBody CourseUpdateDto courseUpdateDto);

    @Operation(summary = "根据id返回课程" , description = "根据id返回课程信息")
    @GetMapping("/manage/select")
    Result<CourseVo> selectCourse(@RequestParam("id") Long id);

    @Operation(summary = "删除一个课程" , description = "删除一个课程")
    @DeleteMapping("/manage/delete")
    Result<CourseVo> deleteCourse(@RequestParam("id") Long id);

    @Operation(summary = "删除多个课程" , description = "删除多个课程")
    @DeleteMapping("/manage/delete-all")
    Result<List<CourseVo>> deleteAllCourse(List<Long> ids);

    @Operation(summary = "课程分页查询" , description = "课程分页查询")
    @GetMapping("/manage/query")
    PageResult<CourseVo> queryCourse(CourseQueryDto courseQueryDto);

}
