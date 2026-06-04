package com.wzc.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.User;
import com.wzc.common.mapper.UserMapper;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员用户管理控制器
 */
@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserMapper userMapper;

    /**
     * 分页获取用户列表
     */
    @GetMapping("/list")
    public ResultUtil<IPage<User>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<User> pageParam = new Page<>(page, size);
        IPage<User> result = userMapper.selectPageByKeyword(pageParam, keyword);
        result.getRecords().forEach(u -> u.setPassword(null));
        return ResultUtil.success(result);
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/status/{id}/{status}")
    public ResultUtil<String> changeStatus(@PathVariable Long id, @PathVariable Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
        return ResultUtil.success(status == 1 ? "启用成功" : "禁用成功");
    }
}
