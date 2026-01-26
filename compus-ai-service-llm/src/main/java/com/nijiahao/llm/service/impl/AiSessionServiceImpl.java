package com.nijiahao.llm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.common.core.utils.CRUDUTILS;
import com.nijiahao.llm.api.dto.po.AiMessagePo;
import com.nijiahao.llm.api.dto.po.AiSessionPo;
import com.nijiahao.llm.api.dto.req.AiSessionUpdateDto;
import com.nijiahao.llm.api.dto.res.AiSessionVo;
import com.nijiahao.llm.mapper.AiMessageMapper;
import com.nijiahao.llm.mapper.AiSessionMapper;
import com.nijiahao.llm.service.AiChatService;
import com.nijiahao.llm.service.AiSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AiSessionServiceImpl extends ServiceImpl<AiSessionMapper, AiSessionPo> implements AiSessionService {

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private AiSessionMapper aiSessionMapper;

    @Autowired
    private AiMessageMapper aiMessageMapper;

    @Override
    public AiSessionVo sessionAdd(Long robotId, Long userId) {
        if (robotId == null || userId == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "机器人id或用户id不能为空");
        }
        Long sessionId = aiChatService.createSession(robotId, userId);
        if (sessionId == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "会话创建失败");
        }
        //查出刚存入的会话表
        AiSessionPo aiSessionPo = aiSessionMapper.selectById(sessionId);

        if (aiSessionPo == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "无此会话");
        }

        return sessionPo2Vo(aiSessionPo);
    }

    @Override
    public AiSessionVo sessionUpdate(AiSessionUpdateDto aiSessionUpdateDto) {
        if (aiSessionUpdateDto == null || aiSessionUpdateDto.getSessionId() == null || aiSessionUpdateDto.getRevision() == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "修改参数为空 或 被修改会话的id为空 或 revison为空");
        }

        Long sessionId = aiSessionUpdateDto.getSessionId();

        AiSessionPo aiSessionPo = aiSessionMapper.selectById(sessionId);

        if (aiSessionPo == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "会话内容不存在");
        }

        if (!aiSessionPo.getRevision().equals(aiSessionUpdateDto.getRevision())) {
            throw new ServiceException(ResultCode.SESSION_ERROR, "当前页面数据已过期，请刷新");
        }

        boolean shouldUpdate = false;

        shouldUpdate |= CRUDUTILS.updateIfChanged(aiSessionUpdateDto.getTitle(), aiSessionPo.getTitle(), aiSessionPo::setTitle);
        shouldUpdate |= CRUDUTILS.updateIfChanged(aiSessionUpdateDto.getIsPinned(), aiSessionPo.getIsPinned(), aiSessionPo::setIsPinned);

        if (shouldUpdate) {
            aiSessionMapper.updateById(aiSessionPo);
        }

        return sessionPo2Vo(aiSessionPo);
    }

    @Override
    @Transactional
    public AiSessionVo sessionDelete(Long sessionId) {
        if (sessionId == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "会话id为空");
        }
        //查出即将被删除的数据
        AiSessionPo aiSessionPo = aiSessionMapper.selectById(sessionId);
        if (aiSessionPo == null) {
            throw new ServiceException(ResultCode.SESSION_ERROR , "不存在这条会话");
        }
        Long currentUserId = StpUtil.getLoginIdAsLong();
        if (!aiSessionPo.getUserId().equals(currentUserId)) {
            throw new ServiceException(ResultCode.SESSION_ERROR, "无权限删除该会话");
        }
        // 1. 删除消息
        aiMessageMapper.delete(
                Wrappers.<AiMessagePo>lambdaQuery()
                        .eq(AiMessagePo::getSessionId, sessionId)
        );
        // 2. 删除会话
        aiSessionMapper.deleteById(sessionId);

        return sessionPo2Vo(aiSessionPo);
    }


    private AiSessionVo sessionPo2Vo(AiSessionPo aiSessionPo) {
        return AiSessionVo.builder()
                .id(aiSessionPo.getId())
                .userId(aiSessionPo.getUserId())
                .robotId(aiSessionPo.getRobotId())
                .isPinned(aiSessionPo.getIsPinned())
                .runtimeConfig(aiSessionPo.getRuntimeConfig())
                .lastMessageContent(aiSessionPo.getLastMessageContent())
                .lastMessageTime(aiSessionPo.getLastMessageTime())
                .messageCount(aiSessionPo.getMessageCount())
                .createTime(aiSessionPo.getCreateTime())
                .build();
    }
}
