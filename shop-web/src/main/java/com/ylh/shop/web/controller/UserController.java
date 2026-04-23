package com.ylh.shop.web.controller;

import com.ylh.shop.common.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 查询个人信息
     * 请求方式：GET
     * 接口地址：/user/1
     * 参数接收：@PathVariable
     */
    @GetMapping("/{userId}")
    public Result<Map<String, Object>> getUserInfo(@PathVariable("userId") Long userId) {
        // 参数校验
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }

        // 模拟返回个人信息数据
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("username", "weizhichun");
        userInfo.put("nickname", "伟志春");
        userInfo.put("phone", "138****8000");
        userInfo.put("email", "weizhichun@example.com");
        userInfo.put("avatar", "https://example.com/avatar.png");

        return Result.success(userInfo);
    }

    /**
     * 修改个人信息
     * 请求方式：PUT
     * 接口地址：/user
     * 参数接收：@RequestBody
     */
    @PutMapping
    public Result<Boolean> updateUserInfo(@RequestBody Map<String, Object> userMap) {
        // 参数提取
        Long userId = Long.valueOf(userMap.get("userId").toString());
        String nickname = (String) userMap.get("nickname");
        String email = (String) userMap.get("email");

        // 参数校验
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            return Result.error("昵称不能为空");
        }

        // 模拟修改成功
        return Result.success(true);
    }

    /**
     * 密码重置
     * 请求方式：POST
     * 接口地址：/user/reset-password
     * 参数接收：@RequestBody
     */
    @PostMapping("/reset-password")
    public Result<Boolean> resetPassword(@RequestBody Map<String, Object> paramMap) {
        // 参数提取
        String phone = (String) paramMap.get("phone");
        String code = (String) paramMap.get("code");
        String newPassword = (String) paramMap.get("newPassword");

        // 参数校验
        if (phone == null || phone.length() != 11) {
            return Result.error("手机号格式不正确");
        }
        if (code == null || code.trim().isEmpty()) {
            return Result.error("验证码不能为空");
        }
        if (newPassword == null || newPassword.length() < 6) {
            return Result.error("新密码长度不能少于6位");
        }

        // 模拟重置成功
        return Result.success(true);
    }
}