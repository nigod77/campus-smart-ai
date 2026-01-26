package com.nijiahao.llm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nijiahao.llm.api.dto.po.AiMessagePo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiMessageMapper extends BaseMapper<AiMessagePo> {
}
