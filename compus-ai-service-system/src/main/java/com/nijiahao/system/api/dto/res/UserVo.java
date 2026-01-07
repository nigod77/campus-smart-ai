package com.nijiahao.system.api.dto.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickName;

    private String userName;

    private Integer identity;

    private Long classId;

    private String image;

    private String workId;

    private Integer gender;

    private Integer revision;

    private LocalDateTime createTime;

    private String className;


}
