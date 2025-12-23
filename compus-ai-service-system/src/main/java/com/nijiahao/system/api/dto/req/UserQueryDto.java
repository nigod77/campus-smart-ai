package com.nijiahao.system.api.dto.req;

import lombok.Data;

@Data
public class UserQueryDto {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String nickname;

    private Long workId;

    private Integer identity;

    private Long classId;

}
