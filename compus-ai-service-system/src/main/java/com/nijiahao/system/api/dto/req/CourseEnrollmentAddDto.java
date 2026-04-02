package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseEnrollmentAddDto {


    private Long id;
    /**
     * 学生ID (关联 sys_user)，与 studentWorkId 二选一
     */
    private Long studentId;

    /**
     * 课程ID (关联 campus_course)，与 courseName+termName 二选一
     */
    private Long courseId;

    // ================= 冗余提速 =================

    /**
     * 学期ID (冗余字段，用于加速查询)；若按课程名解析可自动填入
     */
    private Long termId;

    /** 学生学号/工号（学生 identity=1） */
    private String studentWorkId;

    private String courseName;

    private String termName;

    /** 同名课程冲突时传任课教师昵称或账号 */
    private String teacherNickName;

}
