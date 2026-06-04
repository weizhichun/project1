package com.wzc.web.controller.api;

import com.wzc.common.utils.ResultUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestApiController {

    @GetMapping("/hello")
    public ResultUtil<Map<String, Object>> hello(@RequestParam(required = false) String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello, " + (name != null ? name : "World") + "!");
        data.put("timestamp", System.currentTimeMillis());
        return ResultUtil.success(data);
    }

    @PostMapping("/user")
    public ResultUtil<UserDto> user(@RequestBody UserDto user) {
        user.setId(System.currentTimeMillis());
        user.setCreateTime(System.currentTimeMillis());
        return ResultUtil.success(user);
    }

    @Data
    public static class UserDto {
        private Long id;
        private String username;
        private String nickname;
        private String email;
        private Long createTime;
    }
}
