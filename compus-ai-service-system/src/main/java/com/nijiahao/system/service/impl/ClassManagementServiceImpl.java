package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.ClassPo;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.ClassAddDto;
import com.nijiahao.system.api.dto.req.ClassQueryDto;
import com.nijiahao.system.api.dto.req.ClassUpdateDto;
import com.nijiahao.system.api.dto.res.ClassVo;
import com.nijiahao.system.mapper.ClassMapper;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.ClassManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassManagementServiceImpl extends ServiceImpl<ClassMapper , ClassPo> implements ClassManagementService {

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ClassVo addClass(ClassAddDto classAddDto) {

        ClassPo classPo = new ClassPo();

        BeanUtils.copyProperties(classAddDto,classPo);

        classMapper.insert(classPo);

        ClassVo classVo = new ClassVo();

        BeanUtils.copyProperties(classAddDto,classVo);

        return classVo;
    }

    @Override
    public ClassVo updateClass(ClassUpdateDto classUpdateDto) {
        //1. 参数校验
        if (classUpdateDto.getId() == null ||classUpdateDto.getRevision() == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        //2.查出旧数据
        ClassPo oldClassPo = classMapper.selectById(classUpdateDto.getId());
        if (oldClassPo == null){
            throw new ServiceException(ResultCode.CLASS_NOT_EXIST);
        }
        //3.构建参数,并筛选非空
        ClassPo updateEntity = new ClassPo();
        updateEntity.setId(classUpdateDto.getId());
        updateEntity.setRevision(oldClassPo.getRevision());

        boolean hasChanged = false;

        if (classUpdateDto.getClassNumber() != null) {
            updateEntity.setClassNumber(classUpdateDto.getClassNumber());
            hasChanged = true;
        }
        if (classUpdateDto.getMajor() != null) {
            updateEntity.setMajor(classUpdateDto.getMajor());
            hasChanged = true;
        }
        if (classUpdateDto.getGrade() != null) {
            updateEntity.setGrade(classUpdateDto.getGrade());
            hasChanged = true;
        }

        if (hasChanged) {
            int i = classMapper.updateById(updateEntity);
            if (i == 0) {
                throw new ServiceException(ResultCode.DATA_IS_OUTDATED);
            }
        }

        ClassPo latestUser = classMapper.selectById(classUpdateDto.getId());

        return ClassVo.builder()
                .id(latestUser.getId())
                .classNumber(latestUser.getClassNumber())
                .major(latestUser.getMajor())
                .grade(latestUser.getGrade())
                .revision(latestUser.getRevision())
                .build();
    }

    @Override
    public ClassVo selectById(Long id) {
        if (id == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        ClassPo classPo = classMapper.selectById(id);
        return ClassVo.builder()
                .id(classPo.getId())
                .classNumber(classPo.getClassNumber())
                .major(classPo.getMajor())
                .grade(classPo.getGrade())
                .revision(classPo.getRevision())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClassVo delete(Long classId) {
        if (classId == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        ClassPo classPo = classMapper.selectById(classId);
        if (classPo == null) {
            throw new ServiceException(ResultCode.CLASS_NOT_EXIST);
        }
        boolean hasStudent = userMapper.exists(
                Wrappers.lambdaQuery(UserPo.class).eq(UserPo::getClassId, classId)
        );

        if (hasStudent) {
            throw new ServiceException(ResultCode.CLASS_HAS_STUDENTS);
        }
        int i = classMapper.deleteById(classId);
        if (i == 0) {
            throw new ServiceException(ResultCode.CLASS_DELETE_ERROR);
        }

        return ClassVo.builder()
                .id(classPo.getId())
                .classNumber(classPo.getClassNumber())
                .major(classPo.getMajor())
                .grade(classPo.getGrade())
                .revision(classPo.getRevision())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ClassVo> deleteAll(List<Long> classIds) {
        if (classIds == null || classIds.isEmpty()) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        List<ClassPo> classPos = classMapper.selectBatchIds(classIds);
        if (classPos == null || classPos.isEmpty()) {
            throw new ServiceException(ResultCode.CLASS_NOT_EXIST);
        }
        List<Long> validIds = classPos.stream().map(ClassPo::getId).toList();

        List<UserPo> conflictUsers = userMapper.selectList(
                Wrappers.lambdaQuery(UserPo.class)
                        .select(UserPo::getClassId)
                        .in(UserPo::getClassId, validIds)
                        .groupBy(UserPo::getClassId)
        );
        if (!conflictUsers.isEmpty()){
            Set<Long> conflictIdSet = conflictUsers.stream()
                    .map(UserPo::getClassId)
                    .collect(Collectors.toSet());

            String errorMsg = classPos.stream()
                    .filter(po -> conflictIdSet.contains(po.getId()))
                    .map(po -> po.getGrade() + "级" + po.getMajor() + po.getClassNumber() + "班")
                    .collect(Collectors.joining("，"));
            throw new ServiceException(ResultCode.CLASS_HAS_STUDENTS.getCode(), "以下班级仍有学生，无法删除：" + errorMsg);

        }

        classMapper.deleteByIds(validIds);

        return classPos.stream().map(po-> ClassVo.builder()
                .id(po.getId())
                .classNumber(po.getClassNumber())
                .major(po.getMajor())
                .grade(po.getGrade())
                .revision(po.getRevision())
                .build()).toList();

    }

    @Override
    public PageResult<ClassVo> pageQuery(ClassQueryDto classQueryDto) {
        //1.构建mp分页对象
        Page<ClassPo> page = new Page<>(classQueryDto.getPageNo(), classQueryDto.getPageSize());
        //2.构建mp查询条件
        LambdaQueryWrapper<ClassPo> userPoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        userPoLambdaQueryWrapper.eq(classQueryDto.getClassNumber() != null , ClassPo::getClassNumber, classQueryDto.getClassNumber());

        userPoLambdaQueryWrapper.like(classQueryDto.getMajor() != null , ClassPo::getMajor, classQueryDto.getMajor());

        userPoLambdaQueryWrapper.eq(classQueryDto.getGrade() != null , ClassPo::getGrade, classQueryDto.getGrade());

        //3.执行查询
        Page<ClassPo> classPoPage = classMapper.selectPage(page, userPoLambdaQueryWrapper);

        //4.数据转换PO->VO
        List<ClassVo> classVos = classPoPage.getRecords().stream().map(po-> ClassVo.builder()
                .id(po.getId())
                .classNumber(po.getClassNumber())
                .major(po.getMajor())
                .grade(po.getGrade())
                .revision(po.getRevision())
                .build()).toList();

        return PageResult.<ClassVo>builder()
                .list(classVos)
                .Page(page.getPages())
                .Total(page.getTotal())
                .build();
    }
}
