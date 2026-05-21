package com.wzc.admin.controller;

import com.wzc.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class IndexController {

    @GetMapping("/index")
    public ResultUtil<String> index() {
        return ResultUtil.success("欢迎进入电商后台管理系统！");
    }
}