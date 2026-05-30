package com.wzc.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.Product;
import com.wzc.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理页面控制器
 */
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductPageController {

    private final ProductService productService;

    /**
     * 商品列表页面
     */
    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false) String productName,
            Model model) {
        
        Page<Product> page = productService.page(pageNum, 10, productName);
        model.addAttribute("page", page);
        model.addAttribute("productName", productName);
        
        return "admin/product/list";
    }

    /**
     * 添加商品页面
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("isEdit", false);
        model.addAttribute("product", new Product());
        return "admin/product/form";
    }

    /**
     * 编辑商品页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        if (product == null) {
            return "redirect:/admin/product/list?error=商品不存在";
        }
        
        model.addAttribute("isEdit", true);
        model.addAttribute("product", product);
        return "admin/product/form";
    }
}
