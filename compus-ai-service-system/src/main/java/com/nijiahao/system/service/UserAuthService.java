package com.nijiahao.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.api.dto.req.UserLoginDto;
import com.nijiahao.system.api.dto.req.UserRegisterDto;
import com.nijiahao.system.api.dto.res.UserLoginVo;

public interface UserAuthService extends IService<UserPo> {

    void addUser(UserRegisterDto userRegisterDto);

    UserLoginVo userLogin(UserLoginDto userLoginDto);

    void userLogout();
}
