package com.nijiahao.common.core.utils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 辅助方法:如果新的值与旧值不同，则更新旧值
 */
public class CRUDUTILS {
    /**
     * 如果新值不为空，且与旧值不相等，则更新
     * @return 是否发生了更新
     */
    public static <T> boolean updateIfChanged(T newValue, T currentValue, Consumer<T> setter) {
        // 1. 如果新值是 null，通常代表前端没传这个字段，不更新 (保持原样)
        if (newValue == null) {
            return false;
        }

        // 2. 使用 Objects.equals 安全比较 (处理 String, Long 等值比较)
        if (!Objects.equals(newValue, currentValue)) {
            setter.accept(newValue);
            return true;
        }

        return false;
    }
}
