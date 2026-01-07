package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.nijiahao.system.api.dto.res.CourseEnrollmentVo;
import com.nijiahao.system.mapper.CourseEnrollmentMapper;
import com.nijiahao.system.mapper.CourseMapper;
import com.nijiahao.system.mapper.TermMapper;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public CourseEnrollmentVo enrollmentSelectOne(Long id) {
        if (id == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        CourseEnrollmentPo courseEnrollmentPo = courseEnrollmentMapper.selectById(id);
        return CourseEnrollmentVo.builder()
                .courseId(courseEnrollmentPo.getCourseId())
                .studentId(courseEnrollmentPo.getStudentId())
                .termId(courseEnrollmentPo.getTermId())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseEnrollmentVo enrollmentDelete(Long id) {
        if (id == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        CourseEnrollmentPo courseEnrollmentPo = courseEnrollmentMapper.selectById(id);
        if (courseEnrollmentPo == null) {
            throw new ServiceException(ResultCode.COURSE_ENROLLMENT_NOT_EXIST);
        }
        courseEnrollmentMapper.deleteById(id);
        return CourseEnrollmentVo.builder()
                .courseId(courseEnrollmentPo.getCourseId())
                .studentId(courseEnrollmentPo.getStudentId())
                .termId(courseEnrollmentPo.getTermId())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CourseEnrollmentVo> enrollmentDeleteAll(List<Long> ids) {
        if (ids == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        if (ids.isEmpty()) {
            throw new ServiceException(ResultCode.COURSE_ENROLLMENT_NOT_EXIST);
        }
        List<CourseEnrollmentPo> courseEnrollmentPos = courseEnrollmentMapper.selectBatchIds(ids);
        if (courseEnrollmentPos.isEmpty()) {
            throw new ServiceException(ResultCode.COURSE_ENROLLMENT_NOT_EXIST);
        }

        List<Long> newIds = courseEnrollmentPos.stream().map(CourseEnrollmentPo::getId).collect(Collectors.toList());
        courseEnrollmentMapper.deleteByIds(newIds);

        return courseEnrollmentPos.stream().map(po-> CourseEnrollmentVo.builder()
                .courseId(po.getCourseId())
                .studentId(po.getStudentId())
                .termId(po.getTermId())
                .id(po.getId())
                .build()
        ).toList();
    }

    /**
     * 管理端的分页查询,多表查询
     * @param courseEnrollmentQueryDto
     * @return
     */

    @Override
    public PageResult<CourseEnrollmentVo> enrollmentQuery(CourseEnrollmentQueryDto courseEnrollmentQueryDto) {
        if (courseEnrollmentQueryDto == null) {
            courseEnrollmentQueryDto = new CourseEnrollmentQueryDto();
            courseEnrollmentQueryDto.setPageNo(1);
            courseEnrollmentQueryDto.setPageSize(10);
        }
        //1.构建分页mp对象
        Page<CourseEnrollmentVo> page = new Page<>(courseEnrollmentQueryDto.getPageNo(), courseEnrollmentQueryDto.getPageSize());

        //2.调用自定义xml方法
        IPage<CourseEnrollmentVo> result = courseEnrollmentMapper.selectCourseEnrollmentPage(page , courseEnrollmentQueryDto);
        List<CourseEnrollmentVo> records = result.getRecords();

        return PageResult.<CourseEnrollmentVo>builder()
                .Total(result.getTotal())
                .Page(result.getPages())
                .list(records)
                .build();
    }
}
