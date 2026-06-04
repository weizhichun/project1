package com.wzc.web.controller.api;

import com.wzc.common.entity.User;
import com.wzc.common.service.UserService;
import com.wzc.common.utils.ResultUtil;
import javax.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/login")
    public ResultUtil<User> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            user.setPassword(null);
            session.setAttribute("user", user);
            return ResultUtil.success(user);
        } catch (Exception e) {
            return ResultUtil.fail(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResultUtil<String> logout(HttpSession session) {
        session.invalidate();
        return ResultUtil.success("退出成功");
    }

    @GetMapping("/current")
    public ResultUtil<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        return ResultUtil.success(user);
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
