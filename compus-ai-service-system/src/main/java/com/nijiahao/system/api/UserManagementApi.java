package com.nijiahao.system.api;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.system.api.dto.req.UserAddDto;
import com.nijiahao.system.api.dto.req.UserQueryDto;
import com.nijiahao.system.api.dto.req.UserUpdateDto;
import com.nijiahao.system.api.dto.res.UserCourseVo;
import com.nijiahao.system.api.dto.res.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "02.用户管理模块" , description = "包含查询，新增，删除，修改用户")
@RequestMapping("/system/user")
public interface UserManagementApi {

    @Operation(summary = "新增用户" , description = "新增用户")
    @PostMapping("/manage/add")
    Result<UserVo> addUser(@RequestBody UserAddDto userAddDto);

    @Operation(summary = "更新用户信息" , description = "更新用户信息")
    @PostMapping("/manage/update")
    Result<UserVo> updateUser(@RequestBody UserUpdateDto userUpdateDto);

    @Operation(summary = "删除一个用户" , description = "删除一个用户")
    @DeleteMapping("/manage/delete")
    Result<UserVo> deleteUser(@RequestParam(value = "userId" ) Long userId);

    @Operation(summary = "删除多个用户" , description = "删除多个用户")
    @DeleteMapping("/manage/delete-all")
    Result<List<UserVo>> deleteAllUser(@RequestBody List<Long> ids);

    @Operation(summary = "根据id查询一个用户" , description = "根据id查询一个用户")
    @GetMapping("/manage/selectone")
    Result<UserVo> selectOne(@RequestParam(value = "userId" ) Long userId);

    @Operation(summary = "分页查询多个用户" , description = "分页查询")
    @GetMapping("/manage/query")
    PageResult<UserVo> queryUser(UserQueryDto userQueryDto);

    @Operation(summary = "查询一个用户的全部选课/或授课" , description = "根据id查询选课信息")
    @GetMapping("/manage/getcourse")
    Result<List<UserCourseVo>> getCourse(@RequestParam(value = "userId" ) Long userId);

}
