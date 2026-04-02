package com.nijiahao.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.system.api.dto.Po.CourseEnrollmentPo;
import com.nijiahao.system.api.dto.Po.CoursePo;
import com.nijiahao.system.api.dto.Po.TermPo;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.CourseLookDto;
import com.nijiahao.system.api.dto.res.CourseLookVo;
import com.nijiahao.system.mapper.CourseEnrollmentMapper;
import com.nijiahao.system.mapper.CourseMapper;
import com.nijiahao.system.mapper.TermMapper;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.CourseShowService;
import com.nijiahao.system.service.support.NameReferenceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseShowServiceImpl extends ServiceImpl<CourseEnrollmentMapper, CourseEnrollmentPo> implements CourseShowService {

    @Autowired
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private TermMapper termMapper;

    @Autowired
    private NameReferenceResolver nameReferenceResolver;

    @Override
    public List<CourseLookVo> showCourse2Student(CourseLookDto courseLookDto) {
        long studentId = StpUtil.getLoginIdAsLong();

        if (courseLookDto.getTermId() == null && StringUtils.hasText(courseLookDto.getTermName())) {
            courseLookDto.setTermId(nameReferenceResolver.requireTermIdByName(courseLookDto.getTermName()));
        }

        // 1. 处理默认学期
        if (courseLookDto.getTermId() == null) {
            TermPo termPo = termMapper.selectOne(Wrappers.lambdaQuery(TermPo.class).eq(TermPo::getIsCurrent, 1));
            // 防御：如果没有开启的学期，直接返回
            if (termPo == null) return Collections.emptyList();
            courseLookDto.setTermId(termPo.getId());
        }

        // 2. 查选课记录
        List<CourseEnrollmentPo> enrollments = courseEnrollmentMapper.selectList(
                Wrappers.lambdaQuery(CourseEnrollmentPo.class)
                        .eq(CourseEnrollmentPo::getStudentId, studentId)
                        .eq(CourseEnrollmentPo::getTermId, courseLookDto.getTermId())
        );

        // 【关键修复1】如果是新生(没选课)，直接返回，防止下面报错
        if (enrollments.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> courseIds = enrollments.stream()
                .map(CourseEnrollmentPo::getCourseId)
                .collect(Collectors.toSet());

        // 3. 查课程详情
        List<CoursePo> allCourses = courseMapper.selectBatchIds(courseIds);

        // 4. 过滤周次
        List<CoursePo> activeCourses = allCourses.stream()
                // 注意：这里建议把 DTO 里的 weekDay 改名为 weekIndex，不然容易歧义
                .filter(c -> isCourseActive(c, courseLookDto.getWeekDay()))
                .toList();

        // 🔥【关键修复2】如果这周没课，直接返回，省得去查老师
        if (activeCourses.isEmpty()) {
            return Collections.emptyList();
        }

        // 5. 查教师信息 (只查 activeCourses 里的老师，节省性能)
        Set<Long> teacherIds = activeCourses.stream()
                .map(CoursePo::getTeacherId)
                .collect(Collectors.toSet());

        Map<Long, String> teacherMap = new HashMap<>();

        // 🔥【关键修复3】防止 teacherIds 为空导致 SQL 报错 "IN ()"
        if (!teacherIds.isEmpty()) {
            List<UserPo> teachers = userMapper.selectList(
                    Wrappers.lambdaQuery(UserPo.class)
                            .select(UserPo::getId, UserPo::getUserName)
                            .in(UserPo::getId, teacherIds)
            );
            teacherMap = teachers.stream()
                    .collect(Collectors.toMap(UserPo::getId, UserPo::getUserName));
        }

        // 解决 lambda 闭包变量必须 final 的问题
        Map<Long, String> finalTeacherMap = teacherMap;

        // 6. 组装返回
        return activeCourses.stream().map(po -> CourseLookVo.builder()
                        .courseId(po.getId())
                        .courseName(po.getCourseName())
                        // 使用 getOrDefault 防止脏数据导致老师找不到时报错
                        .teacherName(finalTeacherMap.getOrDefault(po.getTeacherId(), "未知教师"))
                        .weekDay(po.getWeekDay())
                        .section(po.getSection())
                        .location(po.getLocation())
                        .sectionCount(po.getSectionCount())
                        .build())
                .toList();
    }

    // 你的 active 判断逻辑没问题，保留即可
    private boolean isCourseActive(CoursePo course, Integer week) {
        if (week == null) return true;
        if (week < course.getStartWeek() || week > course.getEndWeek()) return false;
        if (course.getWeekType() == 1 && week % 2 == 0) return false;
        if (course.getWeekType() == 2 && week % 2 != 0) return false;
        return true;
    }
}
