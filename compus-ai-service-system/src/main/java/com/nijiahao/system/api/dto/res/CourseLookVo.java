package com.nijiahao.system.api.dto.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLookVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    private String courseName;

    private String location;

    private String teacherName;

    private Integer weekDay;

    private Integer section;

    private Integer sectionCount;

    //前端用于展示颜色字段
    private String colorCode;
}
