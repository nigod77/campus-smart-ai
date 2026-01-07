package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.CoursePo;
import com.nijiahao.system.api.dto.Po.TermPo;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.CourseAddDto;
import com.nijiahao.system.api.dto.req.CourseQueryDto;
import com.nijiahao.system.api.dto.req.CourseUpdateDto;
import com.nijiahao.system.api.dto.res.CourseVo;
import com.nijiahao.system.api.dto.res.UserVo;
import com.nijiahao.system.mapper.CourseMapper;
import com.nijiahao.system.mapper.TermMapper;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.CourseManagementService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseManagementServiceImpl extends ServiceImpl<CourseMapper , CoursePo> implements CourseManagementService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TermMapper termMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseVo addCourse(CourseAddDto courseAddDto) {
        if(courseAddDto == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        TermPo termPo = termMapper.selectById(courseAddDto.getTermId());
        if (termPo == null){
            throw new ServiceException(ResultCode.TERM_NOT_EXIST);
        }
        UserPo userPo = userMapper.selectById(courseAddDto.getTeacherId());
        if (userPo == null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        if (userPo.getIdentity() != 2){
            throw new ServiceException(ResultCode.NOT_TEACHER);
        }
        LambdaQueryWrapper<CoursePo> eq = Wrappers.lambdaQuery(CoursePo.class)
                .eq(CoursePo::getCourseName, courseAddDto.getCourseName())
                .eq(CoursePo::getTermId, courseAddDto.getTermId())
                .eq(CoursePo::getTeacherId, courseAddDto.getTeacherId())
                .eq(CoursePo::getWeekDay, courseAddDto.getWeekDay())
                .eq(CoursePo::getSection, courseAddDto.getSection());
        Long count = courseMapper.selectCount(eq);
        if (count > 0){
            throw new ServiceException(ResultCode.COURSE_EXIST);
        }
        CoursePo newCoursePo = new CoursePo();
        BeanUtils.copyProperties(courseAddDto, newCoursePo);

        if(newCoursePo.getWeekType() == null) {
            newCoursePo.setWeekType(0);
        }

        int insert = courseMapper.insert(newCoursePo);

        if (insert == 0) {
            throw new ServiceException(ResultCode.COURSE_INSERT_ERROR);
        }

        CourseVo vo = new CourseVo();
        BeanUtils.copyProperties(newCoursePo, vo);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseVo updateCourse(CourseUpdateDto courseUpdateDto) {
        //1.判断参数
        if (courseUpdateDto == null
                || courseUpdateDto.getId() == null
                || courseUpdateDto.getRevision() == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //2.查出旧数据
        CoursePo oldCoursePo = courseMapper.selectById(courseUpdateDto.getId());
        if (oldCoursePo == null){
            throw new ServiceException(ResultCode.COURSE_NOT_EXIST);
        }
        //3.构建修改参数
        boolean hasChange = false;
        boolean showCheck = false;
        if (!courseUpdateDto.getRevision().equals(oldCoursePo.getRevision())) {
            throw new ServiceException(ResultCode.DATA_IS_OUTDATED); // 提示"数据已变更，请刷新"
        }
        if (courseUpdateDto.getTermId() != null && !(courseUpdateDto.getTermId().equals(oldCoursePo.getTermId()))){
            TermPo termPo = termMapper.selectById(courseUpdateDto.getTermId());
            if (termPo == null){
                throw new ServiceException(ResultCode.TERM_NOT_EXIST);
            }
            oldCoursePo.setTermId(courseUpdateDto.getTermId());
            hasChange = true;
            showCheck = true;
        }
        if (courseUpdateDto.getTeacherId() != null && !(courseUpdateDto.getTeacherId().equals(oldCoursePo.getTeacherId()))){
            UserPo userPo = userMapper.selectById(courseUpdateDto.getTeacherId());
            if (userPo == null){
                throw new ServiceException(ResultCode.USER_NOT_EXIST);
            }
            if (userPo.getIdentity() != 2){
                throw new ServiceException(ResultCode.NOT_TEACHER);
            }
            oldCoursePo.setTeacherId(courseUpdateDto.getTeacherId());
            hasChange = true;
            showCheck = true;
        }
        if(courseUpdateDto.getCourseName() != null && !(courseUpdateDto.getCourseName().equals(oldCoursePo.getCourseName()))){
            oldCoursePo.setCourseName(courseUpdateDto.getCourseName());
            hasChange = true;
            showCheck = true;
        }
        if (courseUpdateDto.getLocation()!= null && !(courseUpdateDto.getLocation().equals(oldCoursePo.getLocation()))){
            oldCoursePo.setLocation(courseUpdateDto.getLocation());
            hasChange = true;
        }
        if (courseUpdateDto.getTeachingClassName() != null && !(courseUpdateDto.getTeachingClassName().equals(oldCoursePo.getTeachingClassName()))){
            oldCoursePo.setTeachingClassName(courseUpdateDto.getTeachingClassName());
            hasChange = true;
        }
        if (courseUpdateDto.getStartWeek() != null && !(courseUpdateDto.getStartWeek().equals(oldCoursePo.getStartWeek()))){
            oldCoursePo.setStartWeek(courseUpdateDto.getStartWeek());
            hasChange = true;
        }
        if (courseUpdateDto.getEndWeek() != null && !(courseUpdateDto.getEndWeek().equals(oldCoursePo.getEndWeek()))){
            oldCoursePo.setEndWeek(courseUpdateDto.getEndWeek());
            hasChange = true;
        }
        if (courseUpdateDto.getWeekType() != null && !courseUpdateDto.getWeekType().equals(oldCoursePo.getWeekType())){
            oldCoursePo.setWeekType(courseUpdateDto.getWeekType());
            hasChange = true;
        }
        if (courseUpdateDto.getWeekDay()!=null && !(courseUpdateDto.getWeekDay().equals(oldCoursePo.getWeekDay()))){
            oldCoursePo.setWeekDay(courseUpdateDto.getWeekDay());
            showCheck = true;
            hasChange = true;
        }

        if (courseUpdateDto.getSection()!=null && !(courseUpdateDto.getSection().equals(oldCoursePo.getSection()))){
            oldCoursePo.setSection(courseUpdateDto.getSection());
            showCheck = true;
            hasChange = true;
        }
        if (courseUpdateDto.getSectionCount() != null && !(courseUpdateDto.getSectionCount().equals(oldCoursePo.getSectionCount()))){
            oldCoursePo.setSectionCount(courseUpdateDto.getSectionCount());
            hasChange = true;
        }
        CourseVo courseVo = new CourseVo();
        if (!hasChange){
            BeanUtils.copyProperties(oldCoursePo, courseVo);
            return courseVo;
        }
        LambdaQueryWrapper<CoursePo> eq = Wrappers.lambdaQuery(CoursePo.class).select(CoursePo::getId).ne(CoursePo::getId, oldCoursePo.getId());
        if (showCheck){
            eq
                    .eq(CoursePo::getCourseName, oldCoursePo.getCourseName())
                    .eq(CoursePo::getTermId, oldCoursePo.getTermId())
                    .eq(CoursePo::getTeacherId, oldCoursePo.getTeacherId())
                    .eq(CoursePo::getWeekDay, oldCoursePo.getWeekDay())
                    .eq(CoursePo::getSection, oldCoursePo.getSection());

            //4.判断是否冲突
            Long count = courseMapper.selectCount(eq);
            if (count > 0){
                throw new ServiceException(ResultCode.COURSE_EXIST);
            }
        }
        //5.入库
        int rows = courseMapper.updateById(oldCoursePo);
        if (rows == 0) {
            throw new ServiceException(ResultCode.DATA_IS_OUTDATED);
        }
        oldCoursePo.setRevision(oldCoursePo.getRevision() + 1);
        BeanUtils.copyProperties(oldCoursePo, courseVo);
        return courseVo;
    }

    @Override
    public CourseVo selectbyId(Long id) {
        if (id == null){
            throw new ServiceException(ResultCode.COURSE_NOT_EXIST);
        }
        CoursePo coursePo = courseMapper.selectById(id);
        if (coursePo == null){
            throw new ServiceException(ResultCode.COURSE_NOT_EXIST);
        }
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(coursePo, courseVo);
        return courseVo;
    }

    //TODO 删除课程之前要看是否有学生绑定了这个课程
    @Override
    public CourseVo deleteById(Long id) {
        if (id == null){
            throw new ServiceException(ResultCode.COURSE_NOT_EXIST);
        }
        CoursePo coursePo = courseMapper.selectById(id);
        if (coursePo == null){
            throw new ServiceException(ResultCode.COURSE_NOT_EXIST);
        }

        return null;
    }

    //TODO 删除课程之前要看是否有学生绑定了这个课程
    @Override
    public List<CourseVo> deleteByIds(List<Long> ids) {
        return List.of();
    }

    @Override
    public PageResult<CourseVo> pageQuery(CourseQueryDto courseQueryDto) {
        if (courseQueryDto == null) {
            courseQueryDto = new CourseQueryDto();
            courseQueryDto.setPageNum(1);
            courseQueryDto.setPageSize(10);
        }
        //1.构建mp分页对象
        Page<CoursePo> page = new Page<>(courseQueryDto.getPageNum() , courseQueryDto.getPageSize());

        //2.构建查询条件
        LambdaQueryWrapper<CoursePo> pageWrapper = Wrappers.lambdaQuery();

        pageWrapper.like(StringUtils.hasText(courseQueryDto.getCourseName()),CoursePo::getCourseName, courseQueryDto.getCourseName());

        pageWrapper.eq(courseQueryDto.getTeacherId() != null , CoursePo::getTeacherId, courseQueryDto.getTeacherId());

        pageWrapper.eq(courseQueryDto.getTermId() != null , CoursePo::getTermId, courseQueryDto.getTermId());

        pageWrapper.like(courseQueryDto.getLocation() != null , CoursePo::getLocation, courseQueryDto.getLocation());

        //3.查询后返回
        Page<CoursePo> coursePoPage = courseMapper.selectPage(page, pageWrapper);
        List<CoursePo> coursePos = coursePoPage.getRecords();
        List<CourseVo> list = coursePos.stream().map(po -> CourseVo.builder()
                .id(po.getId())
                .courseName(po.getCourseName())
                .teacherId(po.getTeacherId())
                .termId(po.getTermId())
                .location(po.getLocation())
                .endWeek(po.getEndWeek())
                .weekDay(po.getWeekDay())
                .section(po.getSection())
                .startWeek(po.getStartWeek())
                .weekType(po.getWeekType())
                .teachingClassName(po.getTeachingClassName())
                .sectionCount(po.getSectionCount())
                .revision(po.getRevision())
                .build()
        ).toList();

        return PageResult.<CourseVo>builder()
                .list(list)
                .Page(page.getPages())
                .Total(page.getTotal())
                .build();
    }


}
