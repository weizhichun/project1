package com.wzc.web.controller;

import com.wzc.common.service.FileService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/web/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResultUtil<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = fileService.upload(file);
        if (Boolean.TRUE.equals(result.get("success"))) {
            return ResultUtil.success((String) result.get("msg"), result);
        }
        return ResultUtil.fail((String) result.get("msg"));
    }

    @PostMapping("/upload/avatar")
    public ResultUtil<Map<String, Object>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = fileService.upload(file);
        if (Boolean.TRUE.equals(result.get("success"))) {
            return ResultUtil.success("头像上传成功", result);
        }
        return ResultUtil.fail((String) result.get("msg"));
    }
}