package com.nijiahao.llm.api.dto.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("campus_ai_user_stat")
public class AiUserStatPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private Integer dailyRequestCount;
    private Integer dailyTokenCost;
    private LocalDateTime lastRequestTime;
    private Long totalTokenCost;
    private Integer vipLevel;

    /**
     * 额度表非常需要乐观锁，防止并发扣款导致余额错误
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;
}