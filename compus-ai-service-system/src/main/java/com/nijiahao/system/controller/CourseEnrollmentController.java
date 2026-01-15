package com.nijiahao.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.CourseEnrollmentApi;
import com.nijiahao.system.api.dto.req.CourseEnrollmentAddDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentQueryDto;
import com.nijiahao.system.api.dto.req.CourseStudentEnrollmentDto;
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;
import com.nijiahao.system.service.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseEnrollmentController implements CourseEnrollmentApi {


    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @Override
    public Result<CourseEnrollmentVo> enrollmentAdd(CourseEnrollmentAddDto courseEnrollmentAddDto) {
        CourseEnrollmentVo courseEnrollmentVo = courseEnrollmentService.enrollmentAdd(courseEnrollmentAddDto);
        return Result.success(courseEnrollmentVo);
    }



    @Override
    public Result<CourseEnrollmentVo> enrollmentSelectOne(Long id) {
        CourseEnrollmentVo courseEnrollmentVo = courseEnrollmentService.enrollmentSelectOne(id);
        return Result.success(courseEnrollmentVo);
    }

    @Override
    public Result<CourseEnrollmentVo> enrollmentDelete(Long id) {
        CourseEnrollmentVo courseEnrollmentVo = courseEnrollmentService.enrollmentDelete(id);
        return Result.success(courseEnrollmentVo);
    }

    @Override
    public Result<List<CourseEnrollmentVo>> enrollmentDeleteAll(List<Long> ids) {
        List<CourseEnrollmentVo> courseEnrollmentVoList = courseEnrollmentService.enrollmentDeleteAll(ids);
        return Result.success(courseEnrollmentVoList);
    }

    @Override
    public PageResult<CourseEnrollmentVo> enrollmentQuery(CourseEnrollmentQueryDto courseEnrollmentQueryDto) {
        return courseEnrollmentService.enrollmentQuery(courseEnrollmentQueryDto);
    }

    @Override
    public Result<CourseEnrollmentVo> studentEnrollment(CourseStudentEnrollmentDto courseStudentEnrollmentDto) {
        Long studentId = StpUtil.getLoginIdAsLong();
        return  Result.success(courseEnrollmentService.studentEnrollment(courseStudentEnrollmentDto, studentId));
    }
}
