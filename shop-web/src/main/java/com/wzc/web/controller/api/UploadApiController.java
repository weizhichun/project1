package com.wzc.web.controller.api;

import com.wzc.common.service.FileService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadApiController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResultUtil<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = fileService.upload(file);
        
        if (Boolean.TRUE.equals(result.get("success"))) {
            Map<String, Object> data = new HashMap<>();
            data.put("url", result.get("url"));
            data.put("pcUrl", result.get("pcUrl"));
            data.put("mobileUrl", result.get("mobileUrl"));
            data.put("fileName", result.get("fileName"));
            data.put("deduplicated", result.get("deduplicated"));
            return ResultUtil.success(data);
        } else {
            return ResultUtil.fail((String) result.get("msg"));
        }
    }
}
