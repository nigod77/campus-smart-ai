package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.CourseEnrollmentPo;
import com.nijiahao.system.api.dto.Po.CoursePo;
import com.nijiahao.system.api.dto.Po.TermPo;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.CourseEnrollmentAddDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentQueryDto;
import com.nijiahao.system.api.dto.req.CourseEnrollmentUpdateDto;
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;
import com.nijiahao.system.api.dto.res.CourseVo;
import com.nijiahao.system.mapper.CourseEnrollmentMapper;
import com.nijiahao.system.mapper.CourseMapper;
import com.nijiahao.system.mapper.TermMapper;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseEnrollmentServiceImpl extends ServiceImpl< CourseEnrollmentMapper,CourseEnrollmentPo > implements CourseEnrollmentService {

    @Autowired
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TermMapper termMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseEnrollmentVo enrollmentAdd(CourseEnrollmentAddDto courseEnrollmentAddDto) {
        //1.判断参数
        if (courseEnrollmentAddDto == null
                || courseEnrollmentAddDto.getCourseId() == null
                ||courseEnrollmentAddDto.getStudentId() == null
                ||courseEnrollmentAddDto.getTermId()==null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        //1.1判断学期 ， 课程 ， 学生 是否存在
        UserPo userPo = userMapper.selectById(courseEnrollmentAddDto.getStudentId());
        TermPo termPo = termMapper.selectById(courseEnrollmentAddDto.getTermId());
        CoursePo coursePo = courseMapper.selectById(courseEnrollmentAddDto.getCourseId());
        if (userPo == null || termPo == null || coursePo == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //2.查表查重
        //2.1构建查询参数
        LambdaQueryWrapper<CourseEnrollmentPo> eq = Wrappers
                .lambdaQuery(CourseEnrollmentPo.class)
                .eq(CourseEnrollmentPo::getCourseId, courseEnrollmentAddDto.getCourseId())
                .eq(CourseEnrollmentPo::getStudentId, courseEnrollmentAddDto.getStudentId());
        //2.2查表
        Long l = courseEnrollmentMapper.selectCount(eq);
        if (l > 0) {
            throw new ServiceException(ResultCode.COURSE_ALREADY_HAS_BEEN_SELECT);
        }
        //3.构建PO , Vo，入表
        CourseEnrollmentPo courseEnrollmentPo = CourseEnrollmentPo.builder()
                .courseId(courseEnrollmentAddDto.getCourseId())
                .studentId(courseEnrollmentAddDto.getStudentId())
                .termId(coursePo.getTermId())
                .build();

        courseEnrollmentMapper.insert(courseEnrollmentPo);

        return CourseEnrollmentVo.builder()
                .courseId(courseEnrollmentPo.getCourseId())
                .studentId(courseEnrollmentPo.getStudentId())
                .termId(coursePo.getTermId())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseEnrollmentVo enrollmentUpdate(CourseEnrollmentUpdateDto courseEnrollmentUpdateDto) {
        return null;
    }

    @Override
    public CourseEnrollmentVo enrollmentSelectOne(Long id) {
        return null;
    }

    @Override
    public CourseEnrollmentVo enrollmentDelete(Long id) {
        return null;
    }

    @Override
    public List<CourseEnrollmentVo> enrollmentDeleteAll(List<Long> ids) {
        return List.of();
    }

    @Override
    public PageResult<CourseEnrollmentVo> enrollmentQuery(CourseEnrollmentQueryDto courseEnrollmentQueryDto) {
        return null;
    }
}
