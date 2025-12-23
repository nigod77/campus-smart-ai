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
    public UserVo updateUser(UserUpdateDto userUpdateDto) {
        // 1. 先查出数据库里的最新数据（包含当前的 revision）
        UserPo userPo = UserPo.builder()
                .id(userUpdateDto.getId())
                .password(userUpdateDto.getPassword())
                .nickName(userUpdateDto.getNickname())
                .gender(userUpdateDto.getGender())
                .image(userUpdateDto.getImage())
                .workId(userUpdateDto.getWorkId())
                .identity(userUpdateDto.getIdentity())
                .revision(userUpdateDto.getRevision())
                .build();

        int i = userMapper.updateById(userPo);

        if(i != 1){
            throw new ServiceException(ResultCode.USER_UPDATE_ERROR);
        }

        UserPo userPo1 = userMapper.selectById(userPo.getId());

        return UserVo.builder()
                .id(userPo1.getId())
                .userName(userPo1.getUserName())
                .nickName(userPo1.getNickName())
                .identity(userPo1.getIdentity())
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
