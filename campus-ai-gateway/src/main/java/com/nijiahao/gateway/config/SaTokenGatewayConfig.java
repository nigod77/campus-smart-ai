package com.nijiahao.gateway.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.fun.SaFunction;
import cn.dev33.satoken.reactor.context.SaReactorHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.nijiahao.common.core.domain.Result;
import com.nijiahao.common.core.domain.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenGatewayConfig {
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                //1.拦截路由（拦截所有请求）
                .addInclude("/**")
                //2.鉴权方法：所有请求都会到此
                .setAuth(obj->{
                    //3.配置白名单与鉴权规则
                    SaRouter.match("/**")
                            .notMatch("/api/campus/system/user/auth/**")
                            .notMatch("/favicon.ico")
                            .notMatch("/nijiahao-ui.html")
                            .notMatch("/nijiahao-ui/**")
                            .notMatch("/v3/api-docs/**")
                            .notMatch("/webjars/**")
                            .notMatch("/api/campus/*/v3/api-docs/**")
                            .check(r -> StpUtil.checkLogin());
                })
                //4.异常处理，checkLogin()失败，这里处理抛出的异常,并返回JSON
                .setError(e-> {
                    if (e instanceof NotLoginException) {
                         return SaResult.get(
                                ResultCode.UNAUTHORIZED.getCode(),
                                ResultCode.UNAUTHORIZED.getMsg(),
                                null
                        );
                    }
                    return SaResult.error(e.getMessage());
                });
    }
}
