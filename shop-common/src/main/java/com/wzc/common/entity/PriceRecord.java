package com.wzc.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("wzc_price_record")
public class PriceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private String platform;
    private BigDecimal price;
    private String productUrl;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime lastUpdated;
    @TableLogic
    private Integer isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
