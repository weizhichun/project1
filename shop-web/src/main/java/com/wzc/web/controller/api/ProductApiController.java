package com.wzc.web.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.PageResult;
import com.wzc.common.entity.Product;
import com.wzc.common.service.ProductService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResultUtil<PageResult<Product>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer size) {
        
        Page<Product> productPage = productService.page(page, size, null);
        
        PageResult<Product> pageResult = new PageResult<>();
        pageResult.setList(productPage.getRecords());
        pageResult.setTotal(productPage.getTotal());
        pageResult.setHasNext(page < productPage.getPages());
        
        return ResultUtil.success(pageResult);
    }

    @GetMapping("/{id}")
    public ResultUtil<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) {
            return ResultUtil.fail("商品不存在");
        }
        return ResultUtil.success(product);
    }
}
