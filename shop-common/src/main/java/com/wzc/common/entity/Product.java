package com.wzc.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("wzc_product")
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String productName;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String description;
    private String mainImage;
    private String images;

    private Integer status;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}