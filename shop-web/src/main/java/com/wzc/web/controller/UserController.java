package com.wzc.web.controller;

import com.wzc.common.entity.User;
import com.wzc.common.service.UserService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/web/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info/{id}")
    public ResultUtil<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResultUtil.fail("用户不存在");
        }
        return ResultUtil.success(user);
    }

    @PutMapping("/profile")
    public ResultUtil<String> updateProfile(@RequestBody Map<String, Object> params) {
        Object idObj = params.get("id");
        if (idObj == null) {
            return ResultUtil.fail("用户ID不能为空");
        }
        Long id;
        try {
            id = Long.valueOf(idObj.toString());
        } catch (NumberFormatException e) {
            return ResultUtil.fail("用户ID格式错误");
        }
        String nickname = (String) params.get("nickname");
        String email = (String) params.get("email");
        String phone = (String) params.get("phone");

        boolean success = userService.updateProfile(id, nickname, email, phone);
        if (success) {
            return ResultUtil.success("个人信息更新成功");
        }
        return ResultUtil.fail("更新失败");
    }

    @PutMapping("/avatar")
    public ResultUtil<String> updateAvatar(@RequestBody Map<String, Object> params) {
        Object idObj = params.get("id");
        if (idObj == null) {
            return ResultUtil.fail("用户ID不能为空");
        }
        Long id;
        try {
            id = Long.valueOf(idObj.toString());
        } catch (NumberFormatException e) {
            return ResultUtil.fail("用户ID格式错误");
        }
        String avatarUrl = (String) params.get("avatarUrl");
        if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
            return ResultUtil.fail("头像URL不能为空");
        }

        boolean success = userService.updateAvatar(id, avatarUrl);
        if (success) {
            return ResultUtil.success("头像更新成功");
        }
        return ResultUtil.fail("头像更新失败");
    }
}