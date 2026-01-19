package com.nijiahao.llm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.common.core.utils.*;
import com.nijiahao.llm.api.dto.po.AiRobotPo;
import com.nijiahao.llm.api.dto.req.AiRobotAddDto;
import com.nijiahao.llm.api.dto.req.AiRobotEditDto;
import com.nijiahao.llm.api.dto.req.AiRobotPageDto;
import com.nijiahao.llm.api.dto.res.AiRobotVo;
import com.nijiahao.llm.mapper.AiRobotMapper;
import com.nijiahao.llm.service.AiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.nijiahao.common.core.utils.CRUDUTILS.updateIfChanged;

@Service
public class AiRobotServiceImpl extends ServiceImpl<AiRobotMapper , AiRobotPo> implements AiRobotService {


    @Autowired
    private AiRobotMapper aiRobotMapper;


    @Override
    public AiRobotVo addRobot(AiRobotAddDto aiRobotAddDto) {
        if (aiRobotAddDto == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //机器人只有id不一样即可 别的都可以一样
        AiRobotPo aiRobotPo = AiRobotPo.builder()
                .robotName(aiRobotAddDto.getRobotName())
                .avatar(aiRobotAddDto.getAvatar())
                .description(aiRobotAddDto.getDescription())
                .welcomeMessage(aiRobotAddDto.getWelcomeMessage())
                .modelName(aiRobotAddDto.getModelName())
                .systemPrompt(aiRobotAddDto.getSystemPrompt())
                .temperature(aiRobotAddDto.getTemperature())
                .topP(aiRobotAddDto.getTopP())
                .historyLimit(aiRobotAddDto.getHistoryLimit())
                .knowledgeConfig(aiRobotAddDto.getKnowledgeConfig())
                .pluginConfig(aiRobotAddDto.getPluginConfig())
                .isPublic(aiRobotAddDto.getIsPublic())
                .status(aiRobotAddDto.getStatus())
                .build();

        boolean success = this.save(aiRobotPo);
        if (!success) {
            throw new ServiceException(ResultCode.ROBOT_CREATE_ERROR);
        }

        return getAiRobotVofromPo(aiRobotPo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiRobotVo updateRobot(AiRobotEditDto aiRobotEditDto) {

        if (aiRobotEditDto == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        //查出被修改的那一条数据
        AiRobotPo aiRobotPo = aiRobotMapper.selectById(aiRobotEditDto.getId());
        if (aiRobotPo == null){
            throw new ServiceException(ResultCode.ROBOT_NOT_EXIST);
        }

        if (!Objects.equals(aiRobotPo.getRevision(), aiRobotEditDto.getRevision())){
            throw new ServiceException(ResultCode.ROBOT_BE_CHANGED_BYOTHER);
        }

        if (aiRobotPo.getRobotName().equals(aiRobotEditDto.getRobotName())){
            return getAiRobotVofromPo(aiRobotPo);
        }else {
            updateIfChanged(aiRobotEditDto.getRobotName() , aiRobotPo.getRobotName() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getAvatar() , aiRobotPo.getAvatar() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getDescription() , aiRobotPo.getDescription() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getWelcomeMessage() , aiRobotPo.getWelcomeMessage() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getModelName() , aiRobotPo.getModelName() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getSystemPrompt() , aiRobotPo.getSystemPrompt() , aiRobotPo::setRobotName);
            updateIfChanged(aiRobotEditDto.getTemperature() , aiRobotPo.getTemperature() , aiRobotPo::setTemperature);
            updateIfChanged(aiRobotEditDto.getTopP() , aiRobotPo.getTopP() , aiRobotPo::setTopP);
            updateIfChanged(aiRobotEditDto.getHistoryLimit() , aiRobotPo.getHistoryLimit() , aiRobotPo::setHistoryLimit);
            updateIfChanged(aiRobotEditDto.getKnowledgeConfig() , aiRobotPo.getKnowledgeConfig() , aiRobotPo::setKnowledgeConfig);
            updateIfChanged(aiRobotEditDto.getPluginConfig() , aiRobotPo.getPluginConfig() , aiRobotPo::setPluginConfig);
            updateIfChanged(aiRobotEditDto.getIsPublic() , aiRobotPo.getIsPublic() , aiRobotPo::setIsPublic);
            updateIfChanged(aiRobotEditDto.getStatus() , aiRobotPo.getStatus() , aiRobotPo::setStatus);
        }


        int row = aiRobotMapper.updateById(aiRobotPo);
        if (row == 0){
            throw new ServiceException(ResultCode.ROBOT_BE_CHANGED_BYOTHER);
        }
        return getAiRobotVofromPo(aiRobotPo);
    }

    @Override
    public AiRobotVo selectRobot(Long robotId) {

        if (robotId == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        AiRobotPo aiRobotPo = aiRobotMapper.selectById(robotId);

        if (aiRobotPo == null){
            throw new ServiceException(ResultCode.ROBOT_NOT_EXIST);
        }

            Long currentUserId = StpUtil.getLoginIdAsLong();
            // 如果机器人不是公开的，且当前用户不是作者，则禁止访问
            if (Boolean.FALSE.equals(aiRobotPo.getIsPublic()) && !aiRobotPo.getCreateUserId().equals(currentUserId)) {
                throw new ServiceException(ResultCode.NO_PERMISSION); // 需要定义一个无权限的错误码
            }
            return getAiRobotVofromPo(aiRobotPo);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiRobotVo deleteRobot(Long robotId) {
        if (robotId == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        AiRobotPo aiRobotPo = aiRobotMapper.selectById(robotId);
        if (aiRobotPo == null){
            throw new ServiceException(ResultCode.ROBOT_NOT_EXIST);
        }
        aiRobotMapper.deleteById(robotId);

        return getAiRobotVofromPo(aiRobotPo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AiRobotVo> deleteAllRobot(List<Long> robotIds) {
        if (robotIds == null || robotIds.isEmpty()){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        List<AiRobotPo> existRobot = aiRobotMapper.selectBatchIds(robotIds);
        if (existRobot == null || existRobot.isEmpty()){
            throw new ServiceException(ResultCode.ROBOT_NOT_EXIST);
        }
        aiRobotMapper.deleteByIds(robotIds);
        return existRobot.stream().map(this::getAiRobotVofromPo).toList();
    }

    @Override
    public PageResult<AiRobotVo> queryRobot(AiRobotPageDto aiRobotPageDto) {
        //1.构建分页mp对象
        Page<AiRobotPo> page = new Page<>(aiRobotPageDto.getPageNum() , aiRobotPageDto.getPageSize());

        //2.构建查询条件
        LambdaQueryWrapper<AiRobotPo> aiRobotPoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        aiRobotPoLambdaQueryWrapper.like(StringUtils.hasText(aiRobotPageDto.getRobotName()) , AiRobotPo::getRobotName, aiRobotPageDto.getRobotName());
        aiRobotPoLambdaQueryWrapper.eq(StringUtils.hasText(aiRobotPageDto.getRobotName()) , AiRobotPo::getModelName, aiRobotPageDto.getModelName());
        aiRobotPoLambdaQueryWrapper.eq(aiRobotPageDto.getIsPublic() != null , AiRobotPo::getIsPublic, aiRobotPageDto.getIsPublic());
        aiRobotPoLambdaQueryWrapper.eq(aiRobotPageDto.getStatus() != null , AiRobotPo::getStatus, aiRobotPageDto.getStatus());
        aiRobotPoLambdaQueryWrapper.orderByDesc(AiRobotPo::getUpdateTime);
        //3.执行查询
        page = aiRobotMapper.selectPage(page, aiRobotPoLambdaQueryWrapper);

        //4.数据转换PO->VO
        List<AiRobotVo> aiRobotList = page.getRecords().stream().map(this::getAiRobotVofromPo).toList();

        return PageResult.<AiRobotVo>builder()
                .list(aiRobotList)
                .Page(page.getPages())
                .Total(page.getTotal())
                .build();
    }

    @Override
    public List<AiRobotVo> robotList() {
        LambdaQueryWrapper<AiRobotPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AiRobotPo::getStatus, true); // 只查状态开启的

        wrapper.orderByDesc(AiRobotPo::getCreateTime);

        List<AiRobotPo> poList = aiRobotMapper.selectList(wrapper);

        return poList.stream().map(this::getAiRobotVofromPo).toList();
    }

    private AiRobotVo getAiRobotVofromPo(AiRobotPo aiRobotPo) {
        return AiRobotVo.builder()
                .id(aiRobotPo.getId())
                .robotName(aiRobotPo.getRobotName())
                .avatar(aiRobotPo.getAvatar())
                .description(aiRobotPo.getDescription())
                .welcomeMessage(aiRobotPo.getWelcomeMessage())
                .modelName(aiRobotPo.getModelName())
                .systemPrompt(aiRobotPo.getSystemPrompt())
                .temperature(aiRobotPo.getTemperature())
                .topP(aiRobotPo.getTopP())
                .historyLimit(aiRobotPo.getHistoryLimit())
                .knowledgeConfig(aiRobotPo.getKnowledgeConfig())
                .pluginConfig(aiRobotPo.getPluginConfig())
                .isPublic(aiRobotPo.getIsPublic())
                .status(aiRobotPo.getStatus())
                .createTime(aiRobotPo.getCreateTime())
                .updateTime(aiRobotPo.getUpdateTime())
                .createUserId(aiRobotPo.getCreateUserId())
                .build();
    }
}
