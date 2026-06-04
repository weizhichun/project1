package com.wzc.web.controller;

import com.wzc.common.entity.User;
import com.wzc.common.service.UserService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/web/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResultUtil<String> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        if (username == null || username.trim().isEmpty()) {
            return ResultUtil.fail("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResultUtil.fail("密码不能为空");
        }
        boolean success = userService.register(username, password, nickname);
        if (success) {
            return ResultUtil.success("注册成功");
        }
        return ResultUtil.fail("注册失败");
    }

    @PostMapping("/login")
    public ResultUtil<User> login(@RequestBody Map<String, String> params, HttpSession session) {
        String username = params.get("username");
        String password = params.get("password");
        if (username == null || username.trim().isEmpty()) {
            return ResultUtil.fail("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResultUtil.fail("密码不能为空");
        }
        User user = userService.login(username, password);
        user.setPassword(null);
        session.setAttribute("user", user);
        return ResultUtil.success(user);
    }

    @PostMapping("/logout")
    public ResultUtil<String> logout(HttpSession session) {
        session.invalidate();
        return ResultUtil.success("退出成功");
    }

    @GetMapping("/session")
    public ResultUtil<User> getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        return ResultUtil.success(user);
    }

    @GetMapping("/info/{id}")
    public ResultUtil<User> getUserInfo(@PathVariable Long id, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultUtil.fail("未登录");
        }
        
        if (!sessionUser.getId().equals(id)) {
            return ResultUtil.fail("无权查看其他用户信息");
        }
        
        User user = userService.getById(id);
        if (user == null) {
            return ResultUtil.fail("用户不存在");
        }
        user.setPassword(null);
        return ResultUtil.success(user);
    }

    @PutMapping("/profile")
    public ResultUtil<String> updateProfile(@RequestBody Map<String, Object> params, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultUtil.fail("未登录");
        }
        Long id = sessionUser.getId();
        String nickname = (String) params.get("nickname");
        String email = (String) params.get("email");
        String phone = (String) params.get("phone");

        boolean success = userService.updateProfile(id, nickname, email, phone);
        if (success) {
            User updatedUser = userService.getById(id);
            updatedUser.setPassword(null);
            session.setAttribute("user", updatedUser);
            return ResultUtil.success("个人信息更新成功");
        }
        return ResultUtil.fail("更新失败");
    }

    @PutMapping("/avatar")
    public ResultUtil<String> updateAvatar(@RequestBody Map<String, Object> params, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultUtil.fail("未登录");
        }
        Long id = sessionUser.getId();
        String avatarUrl = (String) params.get("avatarUrl");
        if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
            return ResultUtil.fail("头像URL不能为空");
        }

        boolean success = userService.updateAvatar(id, avatarUrl);
        if (success) {
            User updatedUser = userService.getById(id);
            updatedUser.setPassword(null);
            session.setAttribute("user", updatedUser);
            return ResultUtil.success("头像更新成功");
        }
        return ResultUtil.fail("头像更新失败");
    }
}