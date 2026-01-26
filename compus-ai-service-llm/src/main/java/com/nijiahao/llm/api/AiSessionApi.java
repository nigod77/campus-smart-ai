package com.nijiahao.llm.api;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.llm.api.dto.req.AiSessionDto;
import com.nijiahao.llm.api.dto.req.AiSessionUpdateDto;
import com.nijiahao.llm.api.dto.res.AiSessionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "03.会话管理接口" , description = "会话管理接口")
@RequestMapping("/llm/session")
public interface AiSessionApi {

    @Operation(summary = "新增一个会话" , description = "新增一个会话")
    @PostMapping("/manage/add")
    Result<AiSessionVo> sessionAdd(@RequestBody AiSessionDto aiSessionDto);

    @Operation(summary = "编辑一个会话" , description = "编辑一个会话")
    @PostMapping("/manage/update")
    Result<AiSessionVo> sessionUpdate(@RequestBody AiSessionUpdateDto aiSessionUpdateDto);

    @Operation(summary = "删除一个会话" , description = "删除一个会话")
    @PostMapping("/manage/delete")
    Result<AiSessionVo> sessionDelete(@RequestParam("sessionId") Long sessionId);

}
