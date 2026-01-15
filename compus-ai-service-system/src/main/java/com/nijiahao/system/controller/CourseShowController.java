package com.nijiahao.system.controller;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.CourseShowApi;
import com.nijiahao.system.api.dto.req.CourseLookDto;
import com.nijiahao.system.api.dto.res.CourseLookVo;
import com.nijiahao.system.service.CourseShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseShowController implements CourseShowApi {

    @Autowired
    private CourseShowService courseShowService;

    @Override
    public Result<List<CourseLookVo>> showCourse(CourseLookDto courseLookDto) {
        return Result.success(courseShowService.showCourse2Student(courseLookDto));
    }

}
