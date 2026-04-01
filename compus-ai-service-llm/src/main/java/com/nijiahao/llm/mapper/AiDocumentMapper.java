package com.nijiahao.llm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nijiahao.llm.api.dto.po.AiDocumentPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiDocumentMapper extends BaseMapper<AiDocumentPo> {
}
