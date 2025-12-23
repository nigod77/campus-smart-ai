package com.nijiahao.system.api.dto.res;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginVo {

    private String msg;

    private String username;

    private String nickname;

    private Integer identity;

    private String workId;

    private Integer gender;

}
