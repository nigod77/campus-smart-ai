package com.nijiahao.llm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.llm.api.dto.po.AiMessagePo;
import com.nijiahao.llm.api.dto.po.AiRobotPo;
import com.nijiahao.llm.api.dto.po.AiSessionPo;
import com.nijiahao.llm.api.dto.req.AiMessageLogDto;
import com.nijiahao.llm.api.dto.req.AiReplayStreamDto;
import com.nijiahao.llm.mapper.AiMessageMapper;
import com.nijiahao.llm.mapper.AiRobotMapper;
import com.nijiahao.llm.mapper.AiSessionMapper;
import com.nijiahao.llm.service.AiChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AiChatServiceImpl implements AiChatService {


    @Autowired
    private AiRobotMapper aiRobotMapper;

    @Autowired
    private AiMessageMapper aiMessageMapper;

    @Autowired
    private AiSessionMapper aiSessionMapper;

    @Qualifier("chatClient")
    @Autowired
    private ChatClient chatClient;

    // =================================================================
    // 基础 CRUD 方法 (修正版)
    // =================================================================

    /**
     * 创建一个会话
     * @param robotId
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSession(Long robotId , Long userId) {
        if (robotId == null || userId == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR , "机器人id或用户id缺失");
        }
        //1.查出使用的是哪一个机器人。
        AiRobotPo aiRobotPo = aiRobotMapper.selectById(robotId);
        if (aiRobotPo == null) {
            throw new ServiceException(ResultCode.LLM_CHAT , "不存在这个机器人");
        }
        //2.
        AiSessionPo session = new AiSessionPo();
        session.setUserId(userId);
        session.setRobotId(robotId);
        session.setTitle(aiRobotPo.getRobotName());
        session.setIsPinned(false);
        //构建配置参数

        Map<String , Object> map = new HashMap<>();
        map.put("robotId", robotId);
        map.put("temperature", aiRobotPo.getTemperature());
        map.put("top_P" , aiRobotPo.getTopP());
        map.put("model_name", aiRobotPo.getModelName());

        session.setRuntimeConfig(map);
        session.setLastMessageContent(aiRobotPo.getWelcomeMessage());
        session.setLastMessageTime(LocalDateTime.now());
        session.setMessageCount(0);

        aiSessionMapper.insert(session);

        //如果有开场白 则把开场白也算一条消息,要存入message表里
        if (aiRobotPo.getWelcomeMessage() != null && !aiRobotPo.getWelcomeMessage().isEmpty()) {
            AiMessageLogDto aiMessageLogDto = new AiMessageLogDto();
            aiMessageLogDto.setSessionId(session.getId());
            aiMessageLogDto.setUserId(0L);
            aiMessageLogDto.setRole("assistant");
            aiMessageLogDto.setContent(session.getLastMessageContent());
            aiMessageLogDto.setType(1);
            aiMessageLogDto.setTokenInput(0);
            aiMessageLogDto.setTokenOutput(0);
            aiMessageLogDto.setStatus(1);

            saveMessage(aiMessageLogDto);

        }
        return session.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveMessage(AiMessageLogDto dto) {
        Assert.notNull(dto.getSessionId(), "sessionId is required");
        Assert.hasText(dto.getRole(), "role is required");
        Assert.hasText(dto.getContent(), "content is required");
        AiMessagePo aiMessagePo = new AiMessagePo();

        aiMessagePo.setUserId(dto.getUserId());

        aiMessagePo.setSessionId(dto.getSessionId());
        aiMessagePo.setRole(dto.getRole());
        aiMessagePo.setContent(dto.getContent());

        aiMessagePo.setType(dto.getType());
        aiMessagePo.setTokenInput(dto.getTokenInput());
        aiMessagePo.setTokenOutput(dto.getTokenOutput());
        aiMessagePo.setStatus(dto.getStatus());
        aiMessagePo.setErrorMsg(dto.getErrorMsg());
        aiMessagePo.setCitations(dto.getCitations());

        aiMessageMapper.insert(aiMessagePo);

        if (Integer.valueOf(1).equals(dto.getStatus())) {

            // 【核心修复】截断预览文本，防止 "Data too long" 报错
            String rawContent = dto.getContent();
            String previewContent = rawContent;

            // 假设你数据库该字段是 VARCHAR(255)，建议截取 100 字以内，加上省略号
            if (rawContent != null && rawContent.length() > 100) {
                previewContent = rawContent.substring(0, 100) + "...";
            }

            LambdaUpdateWrapper<AiSessionPo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AiSessionPo::getId, dto.getSessionId())
                    .set(AiSessionPo::getLastMessageTime, LocalDateTime.now())
                    // 使用截断后的 previewContent
                    .set(AiSessionPo::getLastMessageContent, previewContent)
                    .setSql("message_count = message_count + 1");

            aiSessionMapper.update(null, updateWrapper);
        }

        return aiMessagePo.getId();

    }

    @Override
    public List<AiMessagePo> getHistoryMsg(Long sessionId, int limit , Long userId) {
        //1.查库，查出这个用户的一个会话里的所有记录，按时间先后顺序
        LambdaQueryWrapper<AiMessagePo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AiMessagePo::getSessionId, sessionId)
                .in(AiMessagePo::getUserId, userId , 0L)
                .orderByDesc(AiMessagePo::getCreateTime)
                .last("LIMIT " + limit);
        List<AiMessagePo> list = aiMessageMapper.selectList(queryWrapper);
        Collections.reverse(list);
        return list;

    }

    @Override
    public Flux<String> llmStreamReplay(AiReplayStreamDto aiReplayStreamDto , Long userId) {

        Long sessionId = aiReplayStreamDto.getSessionId();
        String userPrompt = aiReplayStreamDto.getInputMessage();



        // 1. 校验 session
        AiSessionPo session = aiSessionMapper.selectById(sessionId);
        if (session == null) {
            throw new ServiceException(ResultCode.STREAM_REPLY_ERROR, "会话不存在");
        }

        if (!session.getUserId().equals(userId)) {
            throw new ServiceException(ResultCode.STREAM_REPLY_ERROR, "无权限访问该会话");
        }

        // 2. 查 robot（system prompt）
        AiRobotPo robot = aiRobotMapper.selectById(session.getRobotId());
        if (robot == null) {
            throw new ServiceException(ResultCode.LLM_CHAT, "机器人不存在");
        }
        //3.先保存user的信息
        AiMessageLogDto userMsg = new AiMessageLogDto();
        userMsg.setSessionId(sessionId);
        userMsg.setUserId(userId);
        userMsg.setRole("user");
        userMsg.setContent(userPrompt);
        userMsg.setType(1);
        userMsg.setStatus(1);
        userMsg.setTokenInput(0);
        userMsg.setTokenOutput(0);

        saveMessage(userMsg);

        // 4. 构建上下文历史（控制条数）
        List<AiMessagePo> history = getHistoryMsg(sessionId, 20, userId);

        List<Message> messages = new ArrayList<>();
        for (AiMessagePo m : history) {
            if ("user".equals(m.getRole())) {
                messages.add(new UserMessage(m.getContent()));
            } else {
                messages.add(new AssistantMessage(m.getContent()));
            }
        }

        // 5. 用于累积 assistant 完整回复
        StringBuilder fullAnswer = new StringBuilder();

        // 6. 流式调用
        return chatClient.prompt()
                .system(robot.getSystemPrompt())   // 人设 / 规则
                .messages(messages)                // 历史上下文
                .user(userPrompt)                  // 本轮输入
                .stream()
                .content()
                .doOnNext(fullAnswer::append)
                .doOnComplete(() -> {
                    // 7. 流完成后保存 assistant 消息
                    AiMessageLogDto assistantMsg = new AiMessageLogDto();
                    assistantMsg.setSessionId(sessionId);
                    assistantMsg.setUserId(0L);
                    assistantMsg.setRole("assistant");
                    assistantMsg.setContent(fullAnswer.toString());
                    assistantMsg.setType(1);
                    assistantMsg.setStatus(1);
                    assistantMsg.setTokenInput(0);
                    assistantMsg.setTokenOutput(0);

                    saveMessage(assistantMsg);
                })
                .doOnError(e -> {
                    log.error("LLM stream error", e);
                });
    }
}

