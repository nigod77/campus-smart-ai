package com.nijiahao.llm.service;

import com.nijiahao.llm.api.dto.req.AiSessionUpdateDto;
import com.nijiahao.llm.api.dto.res.AiSessionVo;

public interface AiSessionService {
    AiSessionVo sessionAdd(Long robotId, Long userId);

    AiSessionVo sessionUpdate(AiSessionUpdateDto aiSessionUpdateDto);

    AiSessionVo sessionDelete(Long sessionId);
}
