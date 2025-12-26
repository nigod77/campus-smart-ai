package com.nijiahao.system.controller;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.CourseManagementApi;
import com.nijiahao.system.api.dto.req.CourseAddDto;
import com.nijiahao.system.api.dto.req.CourseQueryDto;
import com.nijiahao.system.api.dto.req.CourseUpdateDto;
import com.nijiahao.system.api.dto.res.CourseVo;
import com.nijiahao.system.service.CourseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseManagementController implements CourseManagementApi {

    @Autowired
    private CourseManagementService courseManagementService;

    @Override
    public Result<CourseVo> addCourse(CourseAddDto courseAddDto) {
        CourseVo courseVo = courseManagementService.addCourse(courseAddDto);
        return Result.success(courseVo);
    }

    @Override
    public Result<CourseVo> updateCourse(CourseUpdateDto courseUpdateDto) {
        CourseVo courseVo = courseManagementService.updateCourse(courseUpdateDto);
        return Result.success(courseVo);
    }

    @Override
    public Result<CourseVo> selectCourse(Long id) {
        CourseVo courseVo = courseManagementService.selectbyId(id);
        return Result.success(courseVo);
    }

    @Override
    public Result<CourseVo> deleteCourse(Long id) {
        CourseVo courseVo = courseManagementService.deleteById(id);
        return Result.success(courseVo);
    }

    @Override
    public Result<List<CourseVo>> deleteAllCourse(List<Long> ids) {
        List<CourseVo> courseVos = courseManagementService.deleteByIds(ids);
        return Result.success(courseVos);
    }

    @Override
    public PageResult<CourseVo> queryCourse(CourseQueryDto courseQueryDto) {
        return courseManagementService.pageQuery(courseQueryDto);
    }
}
