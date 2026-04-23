package com.ylh.shop.admin.controller;

import com.ylh.shop.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    /**
     * 分页查询用户列表
     * 请求方式：GET
     * 接口地址：/admin/user/page?username=张三&pageNum=1&pageSize=10
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
        user1.put("phone", "138****8000");
        user1.put("status", 1);
        userList.add(user1);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("userId", 2L);
        user2.put("username", "lisi");
        user2.put("nickname", "李四");
        user2.put("phone", "139****9000");
        user2.put("status", 1);
        userList.add(user2);

        return Result.success(userList);
    }

    /**
     * 根据ID查询用户详情
     * 请求方式：GET
     * 接口地址：/admin/user/1
     * 参数接收：@PathVariable
     */
    @GetMapping("/{userId}")
    public Result<Map<String, Object>> getUserById(@PathVariable("userId") Long userId) {
        // 参数校验
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }

        // 直接返回模拟数据
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("username", "zhangsan");
        user.put("nickname", "张三");
        user.put("phone", "138****8000");
        user.put("email", "zhang****@example.com");
        user.put("status", 1);
        return Result.success(user);
    }

    /**
     * 新增用户
     * 请求方式：POST
     * 接口地址：/admin/user
     * 参数接收：@RequestBody
     * 响应状态：201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Long> addUser(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        String nickname = (String) userMap.get("nickname");

        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.length() < 6) {
            return Result.error("密码长度不能少于6位");
        }

        // 直接返回模拟数据
        return Result.success(1L);
    }

    /**
     * 修改用户信息
     * 请求方式：PUT
     * 接口地址：/admin/user
     * 参数接收：@RequestBody
     */
    @PutMapping
    public Result<Boolean> updateUser(@RequestBody Map<String, Object> userMap) {
        Long userId = Long.valueOf(userMap.get("userId").toString());
        String nickname = (String) userMap.get("nickname");

        // 参数校验
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }

        // 直接返回模拟数据
        return Result.success(true);
    }

    /**
     * 删除用户
     * 请求方式：DELETE
     * 接口地址：/admin/user/1
     * 参数接收：@PathVariable
     */
    @DeleteMapping("/{userId}")
    public Result<Boolean> deleteUser(@PathVariable("userId") Long userId) {
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }

        // 直接返回模拟数据
        return Result.success(true);
    }

    /**
     * 查询用户列表
     * 请求方式：GET
     * 接口地址：/admin/user/list
     * 参数接收：无
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getUserList() {
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