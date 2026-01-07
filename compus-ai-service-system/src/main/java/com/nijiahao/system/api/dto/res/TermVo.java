package com.nijiahao.system.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 学期名
     * 例如：2023-2024学年 第一学期
     */
    private String termName;

    /**
     * 学期开始日期 (对应数据库 DATE 类型)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate termStart;

    /**
     * 学期结束日期 (对应数据库 DATE 类型)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate termEnd;

    /**
     * 是否是本学期
     * true/1-是 false/0-否
     */
    private Boolean isCurrent;
}
