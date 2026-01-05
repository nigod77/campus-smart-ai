package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseEnrollmentUpdateDto {

    private Long id;
    /**
     * 学生ID (关联 sys_user)
     */
    private Long studentId;

    /**
     * 课程ID (关联 campus_course)
     */
    private Long courseId;


    private Integer revision;
}
