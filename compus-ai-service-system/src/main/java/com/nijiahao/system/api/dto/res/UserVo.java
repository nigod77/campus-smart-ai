package com.nijiahao.system.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVo {

    private Long id;

    private String nickName;

    private String userName;

    private Integer identity;

    private Long classId;

    private String image;

    private String workId;

    private Integer gender;

    private Integer revision;


}
