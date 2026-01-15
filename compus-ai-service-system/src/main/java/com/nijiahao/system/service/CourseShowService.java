package com.nijiahao.system.service;

import com.nijiahao.system.api.dto.req.CourseLookDto;
import com.nijiahao.system.api.dto.res.CourseLookVo;

import java.util.List;

public interface CourseShowService {
    List<CourseLookVo> showCourse2Student(CourseLookDto courseLookDto);

}
