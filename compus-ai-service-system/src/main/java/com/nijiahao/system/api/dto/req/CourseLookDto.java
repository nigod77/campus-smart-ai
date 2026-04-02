package com.nijiahao.system.api.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseLookDto {

    private Long termId;

    /** 与 termId 二选一 */
    private String termName;

    private int weekDay;

}
