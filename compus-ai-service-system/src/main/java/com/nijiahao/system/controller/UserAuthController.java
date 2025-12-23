package com.nijiahao.system.controller;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.UserAuthApi;
import com.nijiahao.system.api.dto.req.UserLoginDto;
import com.nijiahao.system.api.dto.req.UserRegisterDto;
import com.nijiahao.system.api.dto.res.UserLoginVo;
import com.nijiahao.system.api.dto.res.UserVo;
import com.nijiahao.system.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController implements UserAuthApi {

    @Autowired
    private UserAuthService userAuthService;

    @Override
    public Result<UserLoginVo> login(@RequestBody UserLoginDto userLoginDto) {
        UserLoginVo userLoginVo = userAuthService.userLogin(userLoginDto);
        return Result.success(userLoginVo);
    }

    @Override
    public Result<UserVo> register(@RequestBody UserRegisterDto userRegisterDto) {
        userAuthService.addUser(userRegisterDto);
        UserVo userVo = UserVo.builder()
                .userName(userRegisterDto.getUsername())
                .nickName(userRegisterDto.getNickname())
                .identity(userRegisterDto.getIdentity())
                .build();
        return Result.success(userVo);
    }

    @Override
    public Result<Void> logout() {
        userAuthService.userLogout();
        return Result.success();
    }
}
