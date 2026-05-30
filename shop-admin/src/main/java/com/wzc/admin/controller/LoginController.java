package com.wzc.admin.controller;

import com.wzc.common.entity.User;
import com.wzc.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 管理员登录控制器
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        User admin = (User) session.getAttribute("admin");
        if (admin != null) {
            return "redirect:/admin/product/list";
        }
        return "admin/login";
    }

    /**
     * 执行登录
     */
    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        try {
            User user = userService.login(username, password);
            
            // 简单的管理员验证（实际应该用角色表）
            if (!"admin".equals(user.getUsername())) {
                model.addAttribute("error", "无管理员权限");
                return "admin/login";
            }
            
            // 保存到session
            user.setPassword(null);
            session.setAttribute("admin", user);
            session.setMaxInactiveInterval(3600); // 1小时过期
            
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/login";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
