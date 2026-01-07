package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.UserLoginDto;
import com.nijiahao.system.api.dto.req.UserRegisterDto;
import com.nijiahao.system.api.dto.res.UserLoginVo;
import com.nijiahao.system.api.dto.res.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


/**
 * 用户认证接口
 */
@Tag(name = "01.用户认证模块" , description = "包含登陆，注册，注销，获取当前用户信息")
@RequestMapping("/system/user")
public interface UserAuthApi {

    @Operation(summary = "用户登陆" , description = "返回token令牌")
    @PostMapping("/auth/login")
    Result<UserLoginVo> login(@RequestBody UserLoginDto userLoginDto);

    @Operation(summary = "用户注册" , description = "注册新账号，注册成功后需重新登录")
    @PostMapping("/auth/register")
    Result<UserVo> register(@RequestBody UserRegisterDto userRegisterDto);

    @Operation(summary = "用户退出" , description = "退出账号")
    @GetMapping("/auth/logout")
    Result<Void> logout();

}
