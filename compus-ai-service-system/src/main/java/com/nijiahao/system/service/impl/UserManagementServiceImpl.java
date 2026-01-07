package com.nijiahao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.UserAddDto;
import com.nijiahao.system.api.dto.req.UserQueryDto;
import com.nijiahao.system.api.dto.req.UserUpdateDto;
import com.nijiahao.system.api.dto.res.UserVo;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl extends ServiceImpl<UserMapper , UserPo> implements UserManagementService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserVo addUser(UserAddDto userAddDto) {
        LambdaQueryWrapper<UserPo> eq = Wrappers.lambdaQuery(UserPo.class).eq(UserPo::getUserName, userAddDto.getUsername()).last("limit 1");
        UserPo userPo1 = userMapper.selectOne(eq);
        if(userPo1 != null){
            throw new ServiceException(ResultCode.USER_USERNAME_EXISTS);
        }
        UserPo userPo = UserPo.builder()
                .userName(userAddDto.getUsername())
                .password(userAddDto.getPassword())
                .nickName(userAddDto.getNickname())
                .gender(userAddDto.getGender())
                .image(userAddDto.getImage())
                .workId(userAddDto.getWorkId())
                .identity(userAddDto.getIdentity())
                .build();
        userMapper.insert(userPo);
        return UserVo.builder()
                .id(userPo.getId())
                .userName(userPo.getUserName())
                .nickName(userPo.getNickName())
                .identity(userAddDto.getIdentity())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVo updateUser(UserUpdateDto userUpdateDto) {
        // 1. 参数基础校验 (防止 ID 为空)
        if (userUpdateDto.getId() == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        // 2.查询数据库当前数据
        UserPo oldUser = userMapper.selectById(userUpdateDto.getId());
        if (oldUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        // 3. 构建更新对象
        UserPo updateEntity = new UserPo();
        updateEntity.setId(userUpdateDto.getId());
        // 如果 updateDto.getRevision() 为空，说明前端没传版本号，这是不合法的请求
        if (userUpdateDto.getRevision() == null) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        updateEntity.setRevision(userUpdateDto.getRevision());

        // 5. 逐个判断字段，有值才更新 (避免 null 或空字符串覆盖原数据)
        boolean hasChange = false;

        if (StringUtils.hasText(userUpdateDto.getNickname())) {
            updateEntity.setNickName(userUpdateDto.getNickname());
            hasChange = true;
        }

        if (StringUtils.hasText(userUpdateDto.getImage())) {
            updateEntity.setImage(userUpdateDto.getImage());
            hasChange = true;
        }

        if (userUpdateDto.getGender() != null) {
            updateEntity.setGender(userUpdateDto.getGender());
            hasChange = true;
        }
        if (userUpdateDto.getClassId() != null) {
            updateEntity.setClassId(userUpdateDto.getClassId());
            hasChange = true;
        }
        // 6. 执行更新
        if (hasChange) {
            int rows = userMapper.updateById(updateEntity);
            if (rows == 0) {
                throw new ServiceException(ResultCode.DATA_IS_OUTDATED);
            }
        }
        // 7. 重新查询最新数据返回 (为了获取最新的 updateTime 和 revision)
        UserPo latestUser = userMapper.selectById(userUpdateDto.getId());
        return UserVo.builder()
                .id(latestUser.getId())
                .userName(latestUser.getUserName())
                .nickName(latestUser.getNickName())
                .identity(latestUser.getIdentity())
                .image(latestUser.getImage())
                .gender(latestUser.getGender())
                .revision(latestUser.getRevision())
                .build();
    }

    @Override
    public UserVo deleteUser(Long userId) {

        UserPo userPo = userMapper.selectById(userId);

        if(userPo == null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }

        UserVo userVo = UserVo.builder()
                .identity(userPo.getIdentity())
                .nickName(userPo.getNickName())
                .userName(userPo.getUserName())
                .id(userId)
                .image(userPo.getImage())
                .classId(userPo.getClassId())
                .build();

        userMapper.deleteById(userId);

        return userVo;
    }

    @Override
    public List<UserVo> deleteAllUser(List<Long> ids) {
        if(ids == null || ids.isEmpty()){
            throw new ServiceException(ResultCode.USER_DELETE_ERROR);
        }
        List<UserPo> userPos = userMapper.selectBatchIds(ids);
        if(!userPos.isEmpty()){
            userMapper.deleteByIds(ids);
        }
        return userPos.stream().map(po-> UserVo.builder()
                .identity(po.getIdentity())
                .nickName(po.getNickName())
                .userName(po.getUserName())
                .id(po.getId())
                .image(po.getImage())
                .classId(po.getClassId())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public UserVo selectone(Long userId) {

        UserPo userPo = userMapper.selectById(userId);

        if (userPo == null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }

        return UserVo.builder()
                .identity(userPo.getIdentity())
                .nickName(userPo.getNickName())
                .userName(userPo.getUserName())
                .id(userId)
                .image(userPo.getImage())
                .classId(userPo.getClassId())
                .build();

    }

    @Override
    public PageResult<UserVo> query(UserQueryDto userQueryDto) {
        //1.构建mp的分页对象
        Page<UserPo> page = new Page<>(userQueryDto.getPageNo() , userQueryDto.getPageSize());
        //2.构建mp查询条件
        LambdaQueryWrapper<UserPo> userPoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        userPoLambdaQueryWrapper.like(userQueryDto.getNickname() != null, UserPo::getNickName, userQueryDto.getNickname());

        userPoLambdaQueryWrapper.eq(userQueryDto.getClassId() != null , UserPo::getClassId , userQueryDto.getClassId());

        userPoLambdaQueryWrapper.eq(userQueryDto.getWorkId() != null , UserPo::getWorkId , userQueryDto.getWorkId());

        userPoLambdaQueryWrapper.eq(userQueryDto.getIdentity() != null , UserPo::getIdentity , userQueryDto.getIdentity());

        userPoLambdaQueryWrapper.orderByDesc(UserPo::getUpdateTime);

        //3.执行查询
        Page<UserPo> userPoPage = userMapper.selectPage(page, userPoLambdaQueryWrapper);

        //4.数据转换PO->VO
        List<UserVo> userVos = userPoPage.getRecords().stream().map(po-> UserVo.builder()
                .id(po.getId())
                .userName(po.getUserName())
                .nickName(po.getNickName())
                .identity(po.getIdentity())
                .classId(po.getClassId())
                .workId(po.getWorkId())
                .image(po.getImage())
                .build()).toList();

        return PageResult.<UserVo>builder()
                .list(userVos)
                .Page(userPoPage.getPages())
                .Total(userPoPage.getTotal())
                .build();

    }

}
