package com.nijiahao.common.core.utils;

import java.util.function.Consumer;

/**
 * 辅助方法:如果新的值与旧值不同，则更新旧值
 */
public class CRUDUTILS {
    private static <T> boolean updateIfChanged(T newValue , T oldValue , Consumer<T> setter){
        if(newValue != oldValue && newValue != null){
            setter.accept(newValue);
            return true;
        }
        return false;
    }
}
