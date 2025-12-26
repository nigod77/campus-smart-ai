package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseAddDto {

    private Long termId;

    private Long teacherId;

    private String courseName;

    private String location;

    private String teachingClassName;

    private Integer startWeek;

    private Integer endWeek;

    private Integer weekType;

    private Integer weekDay;

    private Integer section;

    private Integer sectionCount;




}
