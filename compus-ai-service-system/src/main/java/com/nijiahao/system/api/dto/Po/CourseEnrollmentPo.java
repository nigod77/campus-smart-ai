package com.nijiahao.system.api.dto.Po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nijiahao.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("campus_sys_course_enrollment")
public class CourseEnrollmentPo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 乐观锁版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;

    // ================= 核心关联 =================

    /**
     * 学生ID (关联 sys_user)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;

    /**
     * 课程ID (关联 campus_course)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    // ================= 冗余提速 =================

    /**
     * 学期ID (冗余字段，用于加速查询)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long termId;

}
