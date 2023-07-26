package com.cy.market.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/*定义拦截器*/
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid，若有则放行，无则重定向到登录页面
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object uid = request.getSession().getAttribute("uid");
        if(uid == null){
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
