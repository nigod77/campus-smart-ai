package com.nijiahao.llm.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.llm.api.dto.req.AiRobotAddDto;
import com.nijiahao.llm.api.dto.req.AiRobotEditDto;
import com.nijiahao.llm.api.dto.req.AiRobotPageDto;
import com.nijiahao.llm.api.dto.res.AiRobotVo;

import java.util.List;

public interface AiRobotService {

    AiRobotVo addRobot(AiRobotAddDto aiRobotAddDto);

    AiRobotVo updateRobot(AiRobotEditDto aiRobotEditDto);

    AiRobotVo selectRobot(Long robotId);

    AiRobotVo deleteRobot(Long robotId);

    List<AiRobotVo> deleteAllRobot(List<Long> robotIds);

    PageResult<AiRobotVo> queryRobot(AiRobotPageDto aiRobotPageDto);

    List<AiRobotVo> robotList();
}
