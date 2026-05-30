package com.wzc.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Product;
import com.wzc.common.service.ProductService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/page")
    public ResultUtil<Page<Product>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        Page<Product> page = productService.page(pageNum, pageSize, keyword);
        return ResultUtil.success(page);
    }

    @GetMapping("/{id}")
    public ResultUtil<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) {
            return ResultUtil.fail("商品不存在");
        }
        if (product.getStatus() == null || product.getStatus() != CommonConstant.PRODUCT_STATUS_ON) {
            return ResultUtil.fail("商品已下架");
        }
        return ResultUtil.success(product);
    }
}
