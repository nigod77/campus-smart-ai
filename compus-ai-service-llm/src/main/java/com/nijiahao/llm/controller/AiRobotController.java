package com.nijiahao.llm.controller;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.AiRobotApi;
import com.nijiahao.llm.api.dto.res.AiRobotVo;
import com.nijiahao.llm.service.AiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AiRobotController implements AiRobotApi {

    @Autowired
    private AiRobotService aiRobotService;

    @Override
    public Result<AiRobotVo> addRobot(AiRobotApi robot) {
        AiRobotVo aiRobotVo = aiRobotService.addRobot(robot);
        return Result.success(aiRobotVo);
    }

    @Override
    public Result<AiRobotVo> updateRobot(AiRobotApi robot) {
        AiRobotVo aiRobotVo = aiRobotService.updateRobot(robot);
        return Result.success(aiRobotVo);
    }

    @Override
    public Result<AiRobotVo> selectRobot(Long robotId) {
        AiRobotVo aiRobotVo = aiRobotService.addRobot(robot);
        return Result.success(aiRobotVo);
    }

    @Override
    public Result<AiRobotVo> deleteRobot(Long robotId) {
        AiRobotVo aiRobotVo = aiRobotService.addRobot(robot);
        return Result.success(aiRobotVo);
    }

    @Override
    public Result<List<AiRobotVo>> deleteAllRobot(List<Long> robotIds) {
        AiRobotVo aiRobotVo = aiRobotService.addRobot(robot);
        return Result.success(aiRobotVo);
    }

    @Override
    public PageResult<AiRobotVo> queryRobot(AiRobotApi robot) {
        AiRobotVo aiRobotVo = aiRobotService.addRobot(robot);
        return Result.success(aiRobotVo);
    }
}
