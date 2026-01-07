package com.nijiahao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nijiahao.system.api.dto.Po.CoursePo;
import com.nijiahao.system.api.dto.res.UserCourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<CoursePo> {

    List<UserCourseVo> selectCourseByStudentId(@Param("studentId") Long studentId);
}
