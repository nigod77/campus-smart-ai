package com.nijiahao.system.api.dto.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseVo {


    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    LocalDateTime createTime;

    /**
     * 学期ID (关联 sys_term)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long termId;

    /**
     * 老师ID (关联 sys_user)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long teacherId;

    // ================= 基本信息 =================
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 上课地点 (如: A5-303)
     */
    private String location;

    /**
     * 教学班名称 (展示用，如: 高数A班)
     */
    private String teachingClassName;

}
