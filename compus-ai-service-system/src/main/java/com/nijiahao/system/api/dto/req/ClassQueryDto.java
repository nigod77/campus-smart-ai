package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class ClassQueryDto {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer grade;

    private String major;

    private Integer classNumber;


}
