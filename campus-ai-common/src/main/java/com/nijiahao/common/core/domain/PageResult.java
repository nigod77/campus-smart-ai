package com.nijiahao.common.core.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResult<T> {
    private List<T> list;
    private Long Total;
    private Long Page;
}
