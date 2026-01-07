package com.nijiahao.system.api.dto.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassVo {

    @JsonSerialize(using = ToStringSerializer.class)
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

    private Integer revision;

}
