package com.ylh.shop.web.controller;

import com.ylh.shop.common.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    /**
     * 获取验证码
     * 请求方式：GET
     * 接口地址：/auth/captcha?phone=13800138000
     * 参数接收：@RequestParam
     */
    @GetMapping("/captcha")
    public Result<String> getCaptcha(@RequestParam("phone") String phone) {
        // Controller仅做参数校验
        if (phone == null || phone.length() != 11) {
            return Result.error("手机号格式不正确");
        }
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            return Result.error("手机号格式不正确");
        }
        // 直接返回模拟数据
        return Result.success("验证码已发送到" + phone);
    }

    /**
     * 用户注册
     * 请求方式：POST
     * 接口地址：/auth/register
     * 参数接收：@RequestBody
     */
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody Map<String, Object> registerMap) {
        String username = (String) registerMap.get("username");
        String password = (String) registerMap.get("password");
        String phone = (String) registerMap.get("phone");

        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.length() < 6) {
            return Result.error("密码长度不能少于6位");
        }
        if (phone == null || phone.length() != 11) {
            return Result.error("手机号格式不正确");
        }

        // 直接返回模拟数据
        Map<String, Object> result = new HashMap<>();
        result.put("userId", 1L);
        result.put("username", username);
        result.put("phone", phone);
        return Result.success(result);
    }

    /**
     * 用户登录
     * 请求方式：POST
     * 接口地址：/auth/login
     * 参数接收：@RequestBody
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, Object> loginMap) {
        String username = (String) loginMap.get("username");
        String password = (String) loginMap.get("password");

        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 直接返回模拟数据
        return Result.success("mock-token-" + username + "-123456");
    }

    /**
     * 分页查询用户列表
     * 请求方式：GET
     * 接口地址：/auth/page?username=张三&pageNum=1&pageSize=10
     * 参数接收：@RequestParam
     */
    @GetMapping("/page")
    public Result<List<Map<String, Object>>> getUserPage(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        // 参数校验
        if (pageNum < 1) {
            return Result.error("页码不能小于1");
        }
        if (pageSize < 1 || pageSize > 100) {
            return Result.error("每页条数必须在1-100之间");
        }

        // 直接返回模拟数据
        List<Map<String, Object>> userList = new ArrayList<>();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("userId", 1L);
        user1.put("username", "zhangsan");
        user1.put("nickname", "张三");
        userList.add(user1);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("userId", 2L);
        user2.put("username", "lisi");
        user2.put("nickname", "李四");
        userList.add(user2);

        return Result.success(userList);
    }
}