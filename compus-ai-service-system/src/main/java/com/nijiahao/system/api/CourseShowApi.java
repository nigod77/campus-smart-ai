package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.CourseLookDto;
import com.nijiahao.system.api.dto.res.CourseLookVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "07.课程表的查询")
@RequestMapping("/system/lookcourse")
public interface CourseShowApi {
    @Operation(summary = "课程表的查询（学生），像日历一样" , description = "课程表查询，前端传递 学期id，第几周 参数 在代码中解析出userid 即可")
    @GetMapping("/show/course")
    Result<List<CourseLookVo>> showCourse(CourseLookDto courseLookDto);
}
