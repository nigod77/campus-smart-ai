package com.nijiahao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nijiahao.system.api.dto.Po.CourseEnrollmentPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseEnrollmentMapper extends BaseMapper<CourseEnrollmentPo> {
}
