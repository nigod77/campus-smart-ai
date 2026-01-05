package com.nijiahao.system.api.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseEnrollmentAddDto {


    private Long id;
    /**
     * 学生ID (关联 sys_user)
     */
    @NotNull
    private Long studentId;

    /**
     * 课程ID (关联 campus_course)
     */
    @NotNull
    private Long courseId;

    // ================= 冗余提速 =================

    /**
     * 学期ID (冗余字段，用于加速查询)
     */
    @NotNull
    private Long termId;

}
