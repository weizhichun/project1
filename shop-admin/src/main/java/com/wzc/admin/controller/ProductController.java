package com.wzc.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Product;
import com.wzc.common.service.ProductService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/page")
    public ResultUtil<Page<Product>> page(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (pageNum < 1) {
            return ResultUtil.fail("页码不能小于1");
        }
        if (pageSize < 1 || pageSize > 100) {
            return ResultUtil.fail("每页条数必须在1-100之间");
        }
        Page<Product> page = productService.page(pageNum, pageSize, productName);
        return ResultUtil.success(page);
    }

    @GetMapping("/{id}")
    public ResultUtil<Product> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResultUtil.fail("商品ID不合法");
        }
        Product product = productService.getById(id);
        if (product == null) {
            return ResultUtil.fail("商品不存在");
        }
        return ResultUtil.success(product);
    }

    @PostMapping
    public ResultUtil<String> add(@RequestBody Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return ResultUtil.fail("商品名称不能为空");
        }
        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            return ResultUtil.fail("商品价格必须大于0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            return ResultUtil.fail("商品库存不能小于0");
        }
        boolean success = productService.add(product);
        if (success) {
            return ResultUtil.success("商品添加成功");
        }
        return ResultUtil.fail("商品添加失败");
    }

    @PutMapping
    public ResultUtil<String> update(@RequestBody Product product) {
        if (product.getId() == null || product.getId() <= 0) {
            return ResultUtil.fail("商品ID不合法");
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return ResultUtil.fail("商品名称不能为空");
        }
        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            return ResultUtil.fail("商品价格必须大于0");
        }
        boolean success = productService.update(product);
        if (success) {
            return ResultUtil.success("商品更新成功");
        }
        return ResultUtil.fail("商品更新失败");
    }

    @PutMapping("/status")
    public ResultUtil<String> updateStatus(@RequestBody Map<String, Object> params) {
        Object idObj = params.get("id");
        Object statusObj = params.get("status");
        if (idObj == null || statusObj == null) {
            return ResultUtil.fail("参数不能为空");
        }
        Long id;
        Integer status;
        try {
            id = Long.valueOf(idObj.toString());
            status = Integer.valueOf(statusObj.toString());
        } catch (NumberFormatException e) {
            return ResultUtil.fail("参数格式错误");
        }
        if (id == null || id <= 0) {
            return ResultUtil.fail("商品ID不合法");
        }
        if (status != CommonConstant.PRODUCT_STATUS_ON && status != CommonConstant.PRODUCT_STATUS_OFF) {
            return ResultUtil.fail("状态值不合法");
        }
        boolean success = productService.updateStatus(id, status);
        return success ? ResultUtil.success(status == 1 ? "商品已上架" : "商品已下架") : ResultUtil.fail("操作失败");
    }

    @DeleteMapping("/{id}")
    public ResultUtil<String> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResultUtil.fail("商品ID不合法");
        }
        boolean success = productService.delete(id);
        return success ? ResultUtil.success("商品已删除") : ResultUtil.fail("删除失败");
    }
}