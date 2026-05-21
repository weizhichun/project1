package com.wzc.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    Map<String, Object> upload(MultipartFile file);

    Map<String, Object> uploadEditorImage(MultipartFile file);
}