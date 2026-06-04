package com.wzc.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("wzc_chat_session")
public class ChatSession {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String sessionNo;
    private String title;
    private Integer status;
    @TableLogic
    private Integer isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
