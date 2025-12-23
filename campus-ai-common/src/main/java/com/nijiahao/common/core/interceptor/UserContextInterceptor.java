package com.nijiahao.common.core.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.nijiahao.common.core.domain.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户信息拦截器
 * 作用：在请求开始时，将userId存入ThreadLocal;请求结束时清除
 */
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.尝试获取登陆ID SaToken
        Object loginId = StpUtil.getLoginIdDefaultNull();

        // 2. 如果获取到了，存入上下文
        if (loginId != null) {
            UserContext.setUserId(loginId.toString());
        }else {
            UserContext.setUserId("20031213");
        }
        // 3. 放行请求
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 4. 必须清理！否则在线程池复用情况下，会读取到上一个用户的 ID，导致数据窜改。
        UserContext.clear();
    }
}
