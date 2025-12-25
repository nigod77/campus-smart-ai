package com.nijiahao.system.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.system.api.dto.req.TermAddDto;
import com.nijiahao.system.api.dto.req.TermQueryDto;
import com.nijiahao.system.api.dto.req.TermUpdateDto;
import com.nijiahao.system.api.dto.res.TermVo;

import java.util.List;

public interface TermManagementService {

    TermVo addTerm(TermAddDto termAddDto);

    TermVo update(TermUpdateDto termUpdateDto);

    TermVo deleteTerm(Long id);

    TermVo selectTerm(Long id);

    List<TermVo> deleteTermAll(List<Long> ids);

    PageResult<TermVo> terQueryPage(TermQueryDto termQueryDto);
}
