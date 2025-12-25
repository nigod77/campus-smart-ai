package com.nijiahao.system.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TermAddDto {

    /**
     * 学期名
     * 例如：2023-2024学年 第一学期
     */
    @NotBlank(message = "学期名不能为空")
    private String termName;

    /**
     * 学期开始日期 (对应数据库 DATE 类型)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始时间不能为空")
    private LocalDate termStart;

    /**
     * 学期结束日期 (对应数据库 DATE 类型)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "结束时间不能为空")
    private LocalDate termEnd;

    /**
     * 是否是本学期
     * true/1-是 false/0-否
     */
    @NotNull(message = "是否当前学期不能为空")
    private Boolean isCurrent;

}
