package com.nijiahao.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.UserManagementApi;
import com.nijiahao.system.api.dto.req.UserAddDto;
import com.nijiahao.system.api.dto.req.UserQueryDto;
import com.nijiahao.system.api.dto.req.UserUpdateDto;
import com.nijiahao.system.api.dto.res.UserCourseVo;
import com.nijiahao.system.api.dto.res.UserVo;
import com.nijiahao.system.mapper.CourseMapper;
import com.nijiahao.system.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@SaCheckRole(value = {"admin"})
@RestController
public class UserManagementController implements UserManagementApi {

    @Autowired
    UserManagementService userManagementService;


    @Override
    public Result<UserVo> addUser(@RequestBody UserAddDto userAddDto) {
        UserVo userVo = userManagementService.addUser(userAddDto);
        return Result.success(userVo);
    }

    @Override
    public Result<UserVo> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        UserVo userVo = userManagementService.updateUser(userUpdateDto);
        return Result.success(userVo);
    }

    @Override
    public Result<UserVo> deleteUser(@RequestParam(value = "userId") Long userId) {
        UserVo uservo = userManagementService.deleteUser(userId);
        return Result.success(uservo);
    }

    @Override
    public Result<List<UserVo>> deleteAllUser(@RequestBody List<Long> ids) {
        List<UserVo> userVos = userManagementService.deleteAllUser(ids);
        return Result.success(userVos);
    }

    @Override
    public Result<UserVo> selectOne(@RequestParam(value = "userId") Long userId) {
        UserVo userVo = userManagementService.selectone(userId);
        return Result.success(userVo);
    }

    @Override
    public PageResult<UserVo> queryUser(UserQueryDto userQueryDto) {
        return userManagementService.query(userQueryDto);
    }

    @Override
    public Result<List<UserCourseVo>> getCourse(Long userId) {
        List<UserCourseVo> userCourseVoList = userManagementService.selectUSerCourse(userId);
        return Result.success(userCourseVoList);
    }
}
