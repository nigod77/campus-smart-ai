package com.nijiahao.system.api.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TermQueryDto {

    private String termName;

    private Boolean isCurrent;

    private LocalDate termStart;

    private LocalDate termEnd;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}
