package com.nijiahao.system.api.dto.res;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseVo {

    private Long id;

    /**
     * 乐观锁版本号
     * 插入时填充默认值，更新时自动+1
     */

    private Integer revision;

    // ================= 归属信息 =================

    /**
     * 学期ID (关联 sys_term)
     */
    private Long termId;

    /**
     * 老师ID (关联 sys_user)
     */
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

    // ================= 周次逻辑 =================

    /**
     * 起始周 (如: 1)
     */
    private Integer startWeek;

    /**
     * 结束周 (如: 16)
     */
    private Integer endWeek;

    /**
     * 周次类型
     * 0-全周, 1-单周, 2-双周
     */
    private Integer weekType;

    // ================= 时间逻辑 =================

    /**
     * 周几 (1-7)
     */
    private Integer weekDay;

    /**
     * 开始节次 (如: 1)
     */
    private Integer section;

    /**
     * 连上几节 (默认2)
     */
    private Integer sectionCount;


}
