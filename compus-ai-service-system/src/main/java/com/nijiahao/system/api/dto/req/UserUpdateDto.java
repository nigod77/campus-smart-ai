package com.nijiahao.system.api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {

    private Long id;

    private String password;

    private String nickname;

    private Integer identity;

    private String image;

    private Integer gender;

    private String workId;

    private Integer revision;

    private Long ClassId;

}
