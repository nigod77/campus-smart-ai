package com.nijiahao.system.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.system.api.dto.req.ClassAddDto;
import com.nijiahao.system.api.dto.req.ClassQueryDto;
import com.nijiahao.system.api.dto.req.ClassUpdateDto;
import com.nijiahao.system.api.dto.res.ClassVo;

import java.util.List;

public interface ClassManagementService {

    ClassVo addClass(ClassAddDto classAddDto);

    ClassVo updateClass(ClassUpdateDto classUpdateDto);

    ClassVo selectById(Long id);

    ClassVo delete(Long classId);

    List<ClassVo> deleteAll(List<Long> classIds);

    PageResult<ClassVo> pageQuery(ClassQueryDto classQueryDto);
}
