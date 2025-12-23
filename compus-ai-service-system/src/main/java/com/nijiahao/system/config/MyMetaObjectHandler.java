package com.nijiahao.system.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    private Long getCurrentUserId() {
        if (StpUtil.isLogin()) {
            return StpUtil.getLoginIdAsLong();
        }else {
            return 20031213L;
        }
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        // 自动填充创建时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        // 自动填充修改时间
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        this.strictInsertFill(metaObject, "createUserId", Long.class, getCurrentUserId());

        this.strictInsertFill(metaObject, "updateUserId", Long.class, getCurrentUserId());

        // 1. 填充 revision (默认从 1 开始)
        // 参数：metaObject, 字段名(Java属性名), 字段类型, 默认值
        this.strictInsertFill(metaObject, "revision", Integer.class, 1);

        // 2. 填充 deleted (默认 false/0 未删除)
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);

        this.strictInsertFill(metaObject, "enabled", Boolean.class, true);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充修改时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        // 自动填充修改人
       this.strictUpdateFill(metaObject, "updateUserId", Long.class, getCurrentUserId());
    }
}
