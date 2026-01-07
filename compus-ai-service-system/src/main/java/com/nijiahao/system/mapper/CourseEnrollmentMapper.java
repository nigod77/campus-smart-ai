package com.nijiahao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nijiahao.system.api.dto.Po.CourseEnrollmentPo;
import com.nijiahao.system.api.dto.req.CourseEnrollmentQueryDto;
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CourseEnrollmentMapper extends BaseMapper<CourseEnrollmentPo> {

    /**
     * 三表关联分页查询
     * @param page
     * @param courseEnrollmentQueryDto
     * @return
     */
    IPage<CourseEnrollmentVo> selectCourseEnrollmentPage(Page<CourseEnrollmentVo> page,@Param("param") CourseEnrollmentQueryDto courseEnrollmentQueryDto);
}
