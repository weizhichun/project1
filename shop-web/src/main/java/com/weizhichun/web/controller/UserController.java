package com.weizhichun.web.controller;

import com.weizhichun.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public ResultUtil<String> getUserInfo() {
        return ResultUtil.success("用户信息查询成功，当前用户：测试用户");
    }
}