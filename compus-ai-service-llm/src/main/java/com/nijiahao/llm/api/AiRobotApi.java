package com.nijiahao.llm.api;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.dto.req.AiRobotAddDto;
import com.nijiahao.llm.api.dto.req.AiRobotEditDto;
import com.nijiahao.llm.api.dto.req.AiRobotPageDto;
import com.nijiahao.llm.api.dto.res.AiRobotVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "01.机器人配置接口" , description = "机器人配置")
@RequestMapping("/llm/robot")
public interface AiRobotApi {


    @Operation(summary = "新增机器人" , description = "新增一个机器人")
    @PostMapping("/manage/add")
    Result<AiRobotVo> addRobot(@RequestBody AiRobotAddDto aiRobotAddDto);

    @Operation(summary = "修改机器人" , description = "修改一个机器人")
    @PostMapping("/manage/update")
    Result<AiRobotVo> updateRobot(@RequestBody AiRobotEditDto aiRobotEditDto);

    @Operation(summary = "根据id查询机器人" , description = "查询一个机器人")
    @GetMapping("/manage/select")
    Result<AiRobotVo> selectRobot(@RequestParam Long robotId);

    @Operation(summary = "删除一个机器人" , description = "根据id删除一个机器人")
    @DeleteMapping("/manage/delete")
    Result<AiRobotVo> deleteRobot(@RequestParam Long robotId);

    @Operation(summary = "删除多个机器人" , description = "根据一个id列表删除多个机器人")
    @DeleteMapping("/manage/delete-all")
    Result<List<AiRobotVo>> deleteAllRobot(List<Long> robotIds);

    @Operation(summary = "机器人分页查询" , description = "分页查询机器人")
    @PostMapping("/manage/query")
    PageResult<AiRobotVo> queryRobot(@RequestBody AiRobotPageDto aiRobotPageDto);

    @Operation(summary = "列出所有机器人" , description = "列出所有机器人")
    @GetMapping("/manage/list")
    List<AiRobotVo> robotList();
}
