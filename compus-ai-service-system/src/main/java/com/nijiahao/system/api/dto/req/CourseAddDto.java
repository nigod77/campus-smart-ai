package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class CourseAddDto {

    private Long termId;

    private Long teacherId;

    /** 与 termId 二选一，前端可仅传学期名称 */
    private String termName;

    /** 与 teacherId 二选一，传教师昵称或登录账号 */
    private String teacherName;

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
