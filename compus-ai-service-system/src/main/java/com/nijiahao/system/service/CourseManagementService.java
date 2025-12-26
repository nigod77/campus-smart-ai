package com.nijiahao.system.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.system.api.dto.req.CourseAddDto;
import com.nijiahao.system.api.dto.req.CourseQueryDto;
import com.nijiahao.system.api.dto.req.CourseUpdateDto;
import com.nijiahao.system.api.dto.res.CourseVo;

import java.util.List;

public interface CourseManagementService {
    CourseVo addCourse(CourseAddDto courseAddDto);

    CourseVo updateCourse(CourseUpdateDto courseUpdateDto);

    CourseVo selectbyId(Long id);

    CourseVo deleteById(Long id);

    List<CourseVo> deleteByIds(List<Long> ids);

    PageResult<CourseVo> pageQuery(CourseQueryDto courseQueryDto);
}
