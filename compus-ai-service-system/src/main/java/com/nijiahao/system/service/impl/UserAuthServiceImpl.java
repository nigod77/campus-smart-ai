package com.nijiahao.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.UserLoginDto;
import com.nijiahao.system.api.dto.req.UserRegisterDto;
import com.nijiahao.system.api.dto.res.UserLoginVo;
import com.nijiahao.system.api.dto.res.UserVo;
import com.nijiahao.system.mapper.UserMapper;
import com.nijiahao.system.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserAuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(UserRegisterDto userRegisterDto) {

        LambdaQueryWrapper<UserPo> eq = Wrappers.lambdaQuery(UserPo.class).eq(UserPo::getUserName, userRegisterDto.getUsername());

        UserPo userPo = userMapper.selectOne(eq);

        if (userPo == null) {
            UserPo user = UserPo.builder()
                    .userName(userRegisterDto.getUsername())
                    .password(userRegisterDto.getPassword())
                    .identity(userRegisterDto.getIdentity())
                    .nickName(userRegisterDto.getNickname())
                    .build();
            userMapper.insert(user);
        }else {
            throw new ServiceException(ResultCode.USER_USERNAME_EXISTS);
        }
    }

    @Override
    public UserLoginVo userLogin(UserLoginDto userLoginDto) {
        String password = userLoginDto.getPassword();
        String username = userLoginDto.getUsername();
        LambdaQueryWrapper<UserPo> eq = Wrappers.lambdaQuery(UserPo.class)
                .eq(UserPo::getUserName, username);
        UserPo userPo = userMapper.selectOne(eq);

        if (userPo == null){
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
        if (!password.equals(userLoginDto.getPassword())){
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }

        StpUtil.login(userPo.getId());

        return UserLoginVo.builder()
                .msg("登陆状态为: " + StpUtil.isLogin())
                .username(userPo.getUserName())
                .nickname(userPo.getNickName())
                .gender(userPo.getGender())
                .identity(userPo.getIdentity())
                .workId(userPo.getWorkId())
                .build();
    }

    @Override
    public void userLogout() {
        if (!StpUtil.isLogin()){
            throw new ServiceException(ResultCode.UNAUTHORIZED);
        }
        StpUtil.logout();
    }

    @Override
    public UserVo getInfo() {
        Long loginId = StpUtil.getLoginIdAsLong();
        UserPo userPo = userMapper.selectById(loginId);

        return UserVo.builder()
                .id(userPo.getId())
                .userName(userPo.getUserName())
                .nickName(userPo.getNickName())
                .gender(userPo.getGender())
                .identity(userPo.getIdentity())
                .workId(userPo.getWorkId())
                .image(userPo.getImage())
                .createTime(userPo.getCreateTime())
                .classId(userPo.getClassId())
                .build();
    }
}
