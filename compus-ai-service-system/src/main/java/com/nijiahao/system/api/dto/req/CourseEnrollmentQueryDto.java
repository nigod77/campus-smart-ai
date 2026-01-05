package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseEnrollmentQueryDto {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String courseId;

    private Integer studentId;

    private Long termId;

}
