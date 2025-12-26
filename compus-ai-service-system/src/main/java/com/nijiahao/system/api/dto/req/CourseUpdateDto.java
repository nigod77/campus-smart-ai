package com.nijiahao.system.api.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseUpdateDto {

    private Long id;

    private Integer revision;

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
