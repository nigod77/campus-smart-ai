package com.nijiahao.system.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "用户注册对象")
public class UserRegisterDto {

    @Schema(name = "用户名" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(name = "密码" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "昵称" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(name = "身份" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "身份不能为空")
    private Integer identity;


}
