package com.wzc.common.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PriceComparison {
    private Long productId;
    private String productName;
    private BigDecimal localPrice;
    private List<PlatformPrice> otherPlatforms;

    @Data
    public static class PlatformPrice {
        private String platform;
        private BigDecimal price;
        private String productUrl;
        private BigDecimal diff;
    }
}
