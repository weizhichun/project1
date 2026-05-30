package com.wzc.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Product;
import com.wzc.common.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {

    private final ProductMapper productMapper;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        List<Product> products = productMapper.selectList(null);
        log.info("查询所有商品，数量: {}", products.size());
        return products;
    }

    @GetMapping("/products/on")
    public List<Product> getOnProducts() {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, CommonConstant.PRODUCT_STATUS_ON);
        List<Product> products = productMapper.selectList(wrapper);
        log.info("查询上架商品，数量: {}", products.size());
        return products;
    }

    @GetMapping("/count")
    public Map<String, Object> countProducts() {
        long total = productMapper.selectCount(null);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, CommonConstant.PRODUCT_STATUS_ON);
        long onCount = productMapper.selectCount(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("onSale", onCount);
        log.info("商品统计 - 总数: {}, 上架: {}", total, onCount);
        return result;
    }
}
