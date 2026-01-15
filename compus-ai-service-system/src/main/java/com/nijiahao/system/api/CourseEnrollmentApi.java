package com.nijiahao.system.api;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.CourseEnrollmentAddDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentQueryDto;
import com.nijiahao.system.api.dto.req.CourseStudentEnrollmentDto;
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;
import io.micrometer.core.instrument.internal.OnlyOnceLoggingDenyMeterFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "06.选课关系表")
@RequestMapping("/system/enrollment")
public interface CourseEnrollmentApi {

    @Operation(summary = "添加选课关系" , description = "添加选课关系表")
    @PostMapping("/manage/add")
    Result<CourseEnrollmentVo> enrollmentAdd(@RequestBody CourseEnrollmentAddDto courseEnrollmentAddDto);


    @Operation(summary = "根据id查询一个选课关系" , description = "根据id查询一个选课关系")
    @GetMapping("/manage/select")
    Result<CourseEnrollmentVo> enrollmentSelectOne(@RequestParam(value = "Id", required = true) Long id);

    @Operation(summary = "删除一个选课关系数据" , description = "删除一个选课关系数据")
    @DeleteMapping("/manage/delete")
    Result<CourseEnrollmentVo> enrollmentDelete(@RequestParam(value = "Id", required = true) Long id);

    @Operation(summary = "删除多个选课关系数据" , description = "删除多个选课关系数据")
    @DeleteMapping("/manage/delete-all")
    Result<List<CourseEnrollmentVo>> enrollmentDeleteAll(List<Long> ids);

    @Operation(summary = "选课关系表分页查询" , description = "分页查询")
    @GetMapping("/manage/query")
    PageResult<CourseEnrollmentVo> enrollmentQuery(CourseEnrollmentQueryDto courseEnrollmentQueryDto);

    @SaCheckRole(value = {"student"})
    @Operation(summary = "学生选课" , description = "学生选课")
    @PostMapping("/manage/studentEnrollentCourse")
    Result<CourseEnrollmentVo> studentEnrollment(CourseStudentEnrollmentDto courseStudentEnrollmentDto);

}
