package com.nijiahao.system.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.system.api.dto.req.CourseEnrollmentAddDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentQueryDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentUpdateDto;
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;

import java.util.List;

public interface CourseEnrollmentService {
    CourseEnrollmentVo enrollmentAdd(CourseEnrollmentAddDto courseEnrollmentAddDto);
    
    CourseEnrollmentVo enrollmentSelectOne(Long id);

    CourseEnrollmentVo enrollmentDelete(Long id);

    List<CourseEnrollmentVo> enrollmentDeleteAll(List<Long> ids);

    PageResult<CourseEnrollmentVo> enrollmentQuery(CourseEnrollmentQueryDto courseEnrollmentQueryDto);
}
