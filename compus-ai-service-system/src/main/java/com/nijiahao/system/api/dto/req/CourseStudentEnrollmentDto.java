package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseStudentEnrollmentDto {

    private Long courseId;

    /** 与 courseId 二选一，对应当前学期内的课程名 */
    private String courseName;

}
