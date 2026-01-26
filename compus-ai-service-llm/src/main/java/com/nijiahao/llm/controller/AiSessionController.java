package com.nijiahao.llm.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.AiSessionApi;
import com.nijiahao.llm.api.dto.req.AiSessionDto;
import com.nijiahao.llm.api.dto.req.AiSessionUpdateDto;
import com.nijiahao.llm.api.dto.res.AiSessionVo;
import com.nijiahao.llm.service.AiSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiSessionController implements AiSessionApi {

    @Autowired
    private AiSessionService aiSessionService;

    @Override
    public Result<AiSessionVo> sessionAdd(AiSessionDto aiSessionDto) {
        Long userId = StpUtil.getLoginIdAsLong();
        return Result.success(aiSessionService.sessionAdd(aiSessionDto.getRobotId(),userId));
    }

    @Override
    public Result<AiSessionVo> sessionUpdate(AiSessionUpdateDto aiSessionUpdateDto) {
        return Result.success(aiSessionService.sessionUpdate(aiSessionUpdateDto));
    }

    @Override
    public Result<AiSessionVo> sessionDelete(Long sessionId) {
        return Result.success(aiSessionService.sessionDelete(sessionId));
    }
}
