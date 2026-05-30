package com.wzc.admin.interceptor;

import com.wzc.common.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员登录拦截器
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");
        
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        
        return true;
    }
}
