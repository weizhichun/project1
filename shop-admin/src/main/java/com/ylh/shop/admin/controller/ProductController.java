package com.ylh.shop.admin.controller;

import com.ylh.shop.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product")
public class ProductController {

    /**
     * 商品分页查询
     * 请求方式：GET
     * 接口地址：/admin/product/page?productName=iPhone&pageNum=1&pageSize=10
     * 参数接收：@RequestParam
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> getProductPage(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        // 参数校验
        if (pageNum < 1) {
            return Result.error("页码不能小于1");
        }
        if (pageSize < 1 || pageSize > 100) {
            return Result.error("每页条数必须在1-100之间");
        }

        // 模拟商品列表数据
        List<Map<String, Object>> productList = new ArrayList<>();

        Map<String, Object> p1 = new HashMap<>();
        p1.put("productId", 1L);
        p1.put("productName", "iPhone 15 Pro");
        p1.put("price", 7999.0);
        p1.put("stock", 100);
        p1.put("category", "手机数码");
        p1.put("status", 1);
        productList.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("productId", 2L);
        p2.put("productName", "MacBook Pro 14");
        p2.put("price", 14999.0);
        p2.put("stock", 50);
        p2.put("category", "电脑办公");
        p2.put("status", 1);
        productList.add(p2);

        // 封装分页结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", 2L);
        result.put("list", productList);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return Result.success(result);
    }

    /**
     * 查询商品详情
     * 请求方式：GET
     * 接口地址：/admin/product/1
     * 参数接收：@PathVariable
     */
    @GetMapping("/{productId}")
    public Result<Map<String, Object>> getProductDetail(@PathVariable("productId") Long productId) {
        // 参数校验
        if (productId == null || productId <= 0) {
            return Result.error("商品ID不合法");
        }

        // 模拟商品详情
        Map<String, Object> product = new HashMap<>();
        product.put("productId", productId);
        product.put("productName", "iPhone 15 Pro");
        product.put("price", 7999.0);
        product.put("stock", 100);
        product.put("category", "手机数码");
        product.put("description", "全新A17 Pro芯片，钛金属设计");
        product.put("status", 1);

        return Result.success(product);
    }

    /**
     * 新增商品
     * 请求方式：POST
     * 接口地址：/admin/product
     * 参数接收：@RequestBody
     * 响应状态：201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Long> addProduct(@RequestBody Map<String, Object> productMap) {
        // 参数提取
        String productName = (String) productMap.get("productName");
        Double price = (Double) productMap.get("price");
        Integer stock = (Integer) productMap.get("stock");

        // 参数校验
        if (productName == null || productName.trim().isEmpty()) {
            return Result.error("商品名称不能为空");
        }
        if (price == null || price <= 0) {
            return Result.error("商品价格必须大于0");
        }
        if (stock == null || stock < 0) {
            return Result.error("商品库存不能小于0");
        }

        // 模拟新增成功，返回商品ID
        return Result.success(1L);
    }

    /**
     * 修改商品信息
     * 请求方式：PUT
     * 接口地址：/admin/product
     * 参数接收：@RequestBody
     */
    @PutMapping
    public Result<Boolean> updateProduct(@RequestBody Map<String, Object> productMap) {
        // 参数提取
        Long productId = Long.valueOf(productMap.get("productId").toString());
        String productName = (String) productMap.get("productName");
        Double price = (Double) productMap.get("price");

        // 参数校验
        if (productId == null || productId <= 0) {
            return Result.error("商品ID不合法");
        }
        if (productName == null || productName.trim().isEmpty()) {
            return Result.error("商品名称不能为空");
        }
        if (price == null || price <= 0) {
            return Result.error("商品价格必须大于0");
        }

        // 模拟修改成功
        return Result.success(true);
    }

    /**
     * 删除商品
     * 请求方式：DELETE
     * 接口地址：/admin/product/1
     * 参数接收：@PathVariable
     */
    @DeleteMapping("/{productId}")
    public Result<Boolean> deleteProduct(@PathVariable("productId") Long productId) {
        // 参数校验
        if (productId == null || productId <= 0) {
            return Result.error("商品ID不合法");
        }

        // 模拟删除成功
        return Result.success(true);
    }
}