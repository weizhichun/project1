package com.wzc.web.controller.api;

import com.wzc.common.entity.PriceComparison;
import com.wzc.common.entity.Product;
import com.wzc.common.service.PriceComparisonService;
import com.wzc.common.service.ProductService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/price")
@RequiredArgsConstructor
public class PriceApiController {
    private final PriceComparisonService priceComparisonService;
    private final ProductService productService;

    @GetMapping("/compare/{productId}")
    public ResultUtil<PriceComparison> comparePrice(@PathVariable Long productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            return ResultUtil.fail("商品不存在");
        }
        PriceComparison comparison = priceComparisonService.comparePrice(product);
        return ResultUtil.success(comparison);
    }

    @GetMapping("/history/{productId}")
    public ResultUtil<Object> getPriceHistory(@PathVariable Long productId) {
        return ResultUtil.success(priceComparisonService.getPriceHistory(productId));
    }
}
