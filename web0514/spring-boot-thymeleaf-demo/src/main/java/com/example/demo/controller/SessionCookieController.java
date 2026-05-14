package com.example.demo.controller;

import com.example.demo.entity.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class SessionCookieController {

    // ==================== Cookie 操作 ====================
    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "zhangsan");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);
        return "redirect:/cookie-demo";
    }

    @GetMapping("/get-cookie")
    public String getCookie(
            @CookieValue(value = "username", required = false, defaultValue = "guest") String username,
            Model model
    ) {
        model.addAttribute("cookieValue", username);
        return "cookie-demo";
    }

    @GetMapping("/delete-cookie")
    public String deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/cookie-demo";
    }

    @GetMapping("/cookie-demo")
    public String cookieDemo() {
        return "cookie-demo";
    }

    // ==================== Session 操作 ====================
    @GetMapping("/set-session")
    public String setSession(HttpSession session) {
        User user = new User(1L, "zhangsan", "123456", "张三", 20, "zhangsan@qq.com", 1, null, null);
        session.setAttribute("loginUser", user);
        return "redirect:/session-demo";
    }

    @GetMapping("/get-session")
    public String getSession(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("loginUser");
            if (user != null) {
                model.addAttribute("sessionUser", user);
            }
        }
        return "session-demo";
    }

    @GetMapping("/profile")
    public String profile(
            @SessionAttribute(value = "loginUser", required = false) User loginUser,
            Model model
    ) {
        if (loginUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loginUser);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/session-demo")
    public String sessionDemo() {
        return "session-demo";
    }

    // ==================== 登录功能 ====================
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(String username, String password, HttpSession session, Model model) {
        if ("admin".equals(username) && "123456".equals(password)) {
            User user = new User(1L, "admin", "123456", "管理员", 25, "admin@example.com", 1, null, null);
            session.setAttribute("loginUser", user);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/index")
    public String index(@SessionAttribute(value = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }
        return "index";
    }
}