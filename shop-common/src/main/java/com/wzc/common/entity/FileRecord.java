package com.wzc.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wzc_file_record")
public class FileRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String originalName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private String md5;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}