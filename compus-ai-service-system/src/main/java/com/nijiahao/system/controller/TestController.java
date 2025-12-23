package com.nijiahao.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class TestController {

    @GetMapping("/auth/role")
    public String role() {
        return StpUtil.getRoleList().toString();
    }
}
