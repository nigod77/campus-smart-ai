package com.nijiahao.system.config;

import cn.dev33.satoken.stp.StpInterface;
import com.nijiahao.system.api.dto.Po.UserPo;
import com.nijiahao.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        ArrayList<String> permissionList = new ArrayList<>();

        UserPo userPo = userMapper.selectById((Serializable) loginId);

        if (0 == userPo.getIdentity()) {
            permissionList.add("admin");
            permissionList.add("student");
            permissionList.add("teacher");
        }
        if (2 == userPo.getIdentity()) {
            permissionList.add("student");
            permissionList.add("teacher");
        }
        if (1 == userPo.getIdentity()) {
            permissionList.add("student");
        }
        return permissionList;
    }
}
