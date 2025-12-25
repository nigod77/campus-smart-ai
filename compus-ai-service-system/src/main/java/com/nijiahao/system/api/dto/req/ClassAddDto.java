package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class ClassAddDto {


    private Long id;
    /**
     * 年级 (年份当作年级)
     */
    private Integer grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private Integer classNumber;
}
