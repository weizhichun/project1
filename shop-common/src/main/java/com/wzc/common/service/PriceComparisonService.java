package com.wzc.common.service;

import com.wzc.common.entity.PriceComparison;
import com.wzc.common.entity.PriceRecord;
import com.wzc.common.entity.Product;
import java.util.List;

public interface PriceComparisonService {
    PriceComparison comparePrice(Product product);
    List<PriceRecord> getPriceHistory(Long productId);
    void refreshPrice(Long productId);
}
