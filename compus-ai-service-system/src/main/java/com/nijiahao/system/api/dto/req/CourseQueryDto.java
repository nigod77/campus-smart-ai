package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseQueryDto {

    private String courseName;

    private String termId;

    private String teacherId;

    private String termName;

    private String teacherName;

    private String classId;

    private String location;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
