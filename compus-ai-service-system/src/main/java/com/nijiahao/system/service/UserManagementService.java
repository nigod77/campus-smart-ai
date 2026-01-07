package com.nijiahao.system.service;

import com.nijiahao.common.core.domain.PageResult;
import com.nijiahao.system.api.dto.req.UserAddDto;
import com.nijiahao.system.api.dto.req.UserQueryDto;
import com.nijiahao.system.api.dto.req.UserUpdateDto;
import com.nijiahao.system.api.dto.res.UserCourseVo;
import com.nijiahao.system.api.dto.res.UserVo;

import java.util.List;

public interface UserManagementService {

    UserVo addUser(UserAddDto userAddDto);

    UserVo updateUser(UserUpdateDto userUpdateDto);

    UserVo deleteUser(Long userId);

    List<UserVo> deleteAllUser(List<Long> ids);

    UserVo selectone(Long userId);

    PageResult<UserVo> query(UserQueryDto userQueryDto);

    List<UserCourseVo> selectUSerCourse(Long userId);
}
