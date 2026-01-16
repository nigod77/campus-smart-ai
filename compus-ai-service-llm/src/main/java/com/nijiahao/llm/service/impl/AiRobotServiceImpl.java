package com.nijiahao.llm.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.llm.api.dto.po.AiRobotPo;
import com.nijiahao.llm.mapper.AiRobotMapper;
import com.nijiahao.llm.service.AiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiRobotServiceImpl extends ServiceImpl<AiRobotMapper , AiRobotPo> implements AiRobotService {
    @Autowired
    private AiRobotMapper aoiRobotMapper;
}
