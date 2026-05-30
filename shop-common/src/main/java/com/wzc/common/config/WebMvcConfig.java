package com.wzc.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC配置类
 * 配置静态资源映射，使上传的文件可以通过URL访问
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final FileUploadProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 核心映射规则：URL前缀 → 本地磁盘路径
        // 例如：/uploads/** -> file:uploads/
        String uploadPath = new File(properties.getBasePath()).getAbsolutePath() + File.separator;
        registry.addResourceHandler(properties.getUrlPrefix() + "**")
                .addResourceLocations("file:" + uploadPath);
    }
}
