package com.nijiahao.system.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseEnrollmentVo {

    private Long id;
    /**
     * 学生ID (关联 sys_user)
     */
    private Long studentId;

    /**
     * 课程ID (关联 campus_course)
     */
    private Long courseId;

    // ================= 冗余提速 =================

    /**
     * 学期ID (冗余字段，用于加速查询)
     */
    private Long termId;

    private Integer revision;

    private String courseName;

    private String termName;

    private String studentName;

    private String location;

}
