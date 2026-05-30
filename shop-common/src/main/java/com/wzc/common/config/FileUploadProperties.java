package com.wzc.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置属性类
 * 读取yml中的自定义配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {

    /**
     * 本地磁盘根存储路径
     */
    private String basePath = "uploads/";

    /**
     * 浏览器访问前缀
     */
    private String urlPrefix = "/uploads/";

    /**
     * 单个文件最大字节数
     */
    private long maxFileSize = 10 * 1024 * 1024; // 默认10MB
}
