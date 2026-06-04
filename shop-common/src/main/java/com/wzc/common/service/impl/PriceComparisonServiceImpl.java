package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzc.common.entity.PriceComparison;
import com.wzc.common.entity.PriceRecord;
import com.wzc.common.entity.Product;
import com.wzc.common.mapper.PriceRecordMapper;
import com.wzc.common.service.PriceComparisonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceComparisonServiceImpl implements PriceComparisonService {
    private final PriceRecordMapper priceRecordMapper;

    @Override
    public PriceComparison comparePrice(Product product) {
        PriceComparison comparison = new PriceComparison();
        comparison.setProductId(product.getId());
        comparison.setProductName(product.getProductName());
        comparison.setLocalPrice(product.getPrice());

        LambdaQueryWrapper<PriceRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRecord::getProductId, product.getId())
               .eq(PriceRecord::getIsDeleted, 0)
               .orderByDesc(PriceRecord::getCreateTime);
        List<PriceRecord> records = priceRecordMapper.selectList(wrapper);

        List<PriceComparison.PlatformPrice> otherPlatforms = new ArrayList<>();
        for (PriceRecord record : records) {
            PriceComparison.PlatformPrice pp = new PriceComparison.PlatformPrice();
            pp.setPlatform(record.getPlatform());
            pp.setPrice(record.getPrice());
            pp.setProductUrl(record.getProductUrl());
            if (product.getPrice() != null && record.getPrice() != null) {
                pp.setDiff(record.getPrice().subtract(product.getPrice())
                        .setScale(2, RoundingMode.HALF_UP));
            } else {
                pp.setDiff(BigDecimal.ZERO);
            }
            otherPlatforms.add(pp);
        }
        comparison.setOtherPlatforms(otherPlatforms);
        return comparison;
    }

    @Override
    public List<PriceRecord> getPriceHistory(Long productId) {
        LambdaQueryWrapper<PriceRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRecord::getProductId, productId)
               .eq(PriceRecord::getIsDeleted, 0)
               .orderByDesc(PriceRecord::getCreateTime);
        return priceRecordMapper.selectList(wrapper);
    }

    @Override
    public void refreshPrice(Long productId) {
        log.info("刷新商品 {} 的价格信息", productId);
    }
}
