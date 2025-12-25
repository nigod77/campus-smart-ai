package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.TermPo;
import com.nijiahao.system.api.dto.req.TermAddDto;
import com.nijiahao.system.api.dto.req.TermQueryDto;
import com.nijiahao.system.api.dto.req.TermUpdateDto;
import com.nijiahao.system.api.dto.res.TermVo;
import com.nijiahao.system.mapper.TermMapper;
import com.nijiahao.system.service.TermManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TermManagementServiceImpl extends ServiceImpl<TermMapper , TermPo> implements TermManagementService {

    @Autowired
    private TermMapper termMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TermVo addTerm(TermAddDto termAddDto) {
        //1.判断参数
        if(termAddDto.getTermName() == null
                || termAddDto.getIsCurrent() == null
                || termAddDto.getTermStart()==null
                || termAddDto.getTermEnd()==null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<TermPo> wrapper = Wrappers.lambdaQuery(TermPo.class)
                .select(TermPo::getTermName, TermPo::getIsCurrent);

        //2.判断是否有别的学期正在进行
        if (termAddDto.getIsCurrent()){
            wrapper.eq(TermPo::getIsCurrent, true).or().eq(TermPo::getTermName, termAddDto.getTermName());
        }else {
            wrapper.eq(TermPo::getTermName, termAddDto.getTermName());
        }

        List<TermPo> conflictList = termMapper.selectList(wrapper);

        for (TermPo conflict : conflictList) {
            if (conflict.getTermName().equals(termAddDto.getTermName())) {
                throw new ServiceException(ResultCode.TERM_NAME_REPLY);
            }

            if (Boolean.TRUE.equals(conflict.getIsCurrent())) {
                throw new ServiceException(ResultCode.TERM_ANOTHERTERM_ING);
            }
        }
        //3.存入数据
        TermPo termPo = TermPo.builder()
                .termName(termAddDto.getTermName())
                .isCurrent(termAddDto.getIsCurrent())
                .termStart(termAddDto.getTermStart())
                .termEnd(termAddDto.getTermEnd())
                .build();
        termMapper.insert(termPo);
        return TermVo.builder()
                .id(termPo.getId())
                .termName(termPo.getTermName())
                .isCurrent(termPo.getIsCurrent())
                .termStart(termPo.getTermStart())
                .termEnd(termPo.getTermEnd())
                .build();
    }

    @Override
    public TermVo update(TermUpdateDto termUpdateDto) {
        //1.校验id
        if(termUpdateDto.getId() == null || termUpdateDto.getRevision() == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //2.查出旧数据
        TermPo oldTermPo = termMapper.selectById(termUpdateDto.getId());
        if(oldTermPo == null){
            throw new ServiceException(ResultCode.TERM_NOT_EXIST);
        }
        //3.判断各个字段
        TermPo termPo = new TermPo();
        termPo.setId(termUpdateDto.getId());
        termPo.setRevision(termUpdateDto.getRevision());

        boolean hasChange = false;

        LambdaQueryWrapper<TermPo> wrapper = Wrappers.lambdaQuery(TermPo.class).ne(TermPo::getId, termUpdateDto.getId());

        boolean needCheck = false;

        if(termUpdateDto.getIsCurrent() != null){
            if (Boolean.TRUE.equals(termUpdateDto.getIsCurrent()) && !oldTermPo.getIsCurrent()){
                wrapper.eq(TermPo::getIsCurrent , true);
                needCheck = true;
            }
            termPo.setIsCurrent(termUpdateDto.getIsCurrent());
            oldTermPo.setIsCurrent(termUpdateDto.getIsCurrent());
            hasChange = true;
        }

        if(termUpdateDto.getTermName() != null){
            if (needCheck){
                wrapper.or().eq(TermPo::getTermName, termUpdateDto.getTermName());
            }else {
                wrapper.eq(TermPo::getTermName, termUpdateDto.getTermName());
                needCheck = true;
            }
            termPo.setTermName(termUpdateDto.getTermName());
            oldTermPo.setTermName(termUpdateDto.getTermName());
            hasChange = true;

        }

        // --- 执行冲突检查 (仅当 needCheck 为 true 时) ---
        if (needCheck) {
            List<TermPo> conflicts = termMapper.selectList(wrapper);
            for (TermPo conflict : conflicts) {
                if (termUpdateDto.getTermName() != null && termUpdateDto.getTermName().equals(conflict.getTermName())) {
                    throw new ServiceException(ResultCode.TERM_NAME_REPLY);
                }
                if (Boolean.TRUE.equals(termUpdateDto.getIsCurrent()) && Boolean.TRUE.equals(conflict.getIsCurrent())) {
                    throw new ServiceException(ResultCode.TERM_ANOTHERTERM_ING);
                }
            }
        }
        if(termUpdateDto.getTermStart() != null){
            termPo.setTermStart(termUpdateDto.getTermStart());
            oldTermPo.setTermStart(termUpdateDto.getTermStart());
            hasChange = true;
        }
        if(termUpdateDto.getTermEnd() != null){
            termPo.setTermEnd(termUpdateDto.getTermEnd());
            oldTermPo.setTermEnd(termUpdateDto.getTermEnd());
            hasChange = true;
        }
        if (!hasChange) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        //4.存入数据
        int i = termMapper.updateById(termPo);
        if (i != 1) {
            throw new ServiceException(ResultCode.TERM_UPDATE_ERROR);
        }

        return TermVo.builder()
                .id(oldTermPo.getId())
                .termName(oldTermPo.getTermName())
                .isCurrent(oldTermPo.getIsCurrent())
                .termStart(oldTermPo.getTermStart())
                .termEnd(oldTermPo.getTermEnd())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TermVo deleteTerm(Long id) {
        if(id == null){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //TODO 后续要查是否有关联学期
        TermPo termPo = termMapper.selectById(id);

        if(termPo == null){
            throw new ServiceException(ResultCode.TERM_NOT_EXIST);
        }
        if(termPo.getIsCurrent().equals(Boolean.TRUE) ){
            throw new ServiceException(ResultCode.TERM_PROCESSING_NOW);
        }
        int i = termMapper.deleteById(id);
        if(i != 1){
            throw new ServiceException(ResultCode.TERM_DELETE_ERROR);
        }
        return TermVo.builder()
                .id(termPo.getId())
                .termName(termPo.getTermName())
                .isCurrent(termPo.getIsCurrent())
                .termStart(termPo.getTermStart())
                .termEnd(termPo.getTermEnd())
                .build();
    }

    @Override
    public TermVo selectTerm(Long id) {
        TermPo termPo = termMapper.selectById(id);
        return TermVo.builder()
                .id(termPo.getId())
                .termName(termPo.getTermName())
                .isCurrent(termPo.getIsCurrent())
                .termStart(termPo.getTermStart())
                .termEnd(termPo.getTermEnd())
                .build();
    }
    //TODO 删除多条数据
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TermVo> deleteTermAll(List<Long> ids) {
        if( ids == null || ids.isEmpty()){
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }

        List<TermPo> termPos = termMapper.selectBatchIds(ids);

        if(termPos == null || termPos.isEmpty()){
            throw new ServiceException(ResultCode.TERM_NOT_EXIST);
        }

        List<Long> validIds = termPos.stream().map(TermPo::getId).toList();

        return List.of();
    }

    @Override
    public PageResult<TermVo> terQueryPage(TermQueryDto termQueryDto) {
        //1.构建分页对象
        Page<TermPo> page = new Page<>(termQueryDto.getPageNum(),termQueryDto.getPageSize());

        //2.构建查询条件
        LambdaQueryWrapper<TermPo> queryWrapper = Wrappers.lambdaQuery(TermPo.class);

        queryWrapper.like(termQueryDto.getTermName() != null,TermPo::getTermName, termQueryDto.getTermName());

        queryWrapper.eq(termQueryDto.getIsCurrent() != null,TermPo::getIsCurrent, termQueryDto.getIsCurrent());

        queryWrapper.eq(termQueryDto.getTermStart() != null,TermPo::getTermStart, termQueryDto.getTermStart());

        queryWrapper.eq(termQueryDto.getTermEnd() != null,TermPo::getTermEnd, termQueryDto.getTermEnd());

        //3.开始查询
        Page<TermPo> termPoPage = termMapper.selectPage(page, queryWrapper);

        List<TermVo> termVos  = termPoPage.getRecords().stream().map(po-> TermVo.builder()
                .id(po.getId())
                .termName(po.getTermName())
                .isCurrent(po.getIsCurrent())
                .termStart(po.getTermStart())
                .termEnd(po.getTermEnd())
                .build()).toList();
        //4.转换
        return PageResult.<TermVo>builder()
                .list(termVos)
                .Total(page.getTotal())
                .Page(page.getPages())
                .build();
    }
}
