package com.nijiahao.system.api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddDto {

    private String username;

    private String password;

    private String nickname;

    private Integer identity;

    private String image;

    private Integer gender;

    private String workId;

}
