package com.wzc.admin.controller;

import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Product;
import com.wzc.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 商品管理接口控制器
 */
@Slf4j
@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${file.upload.path:uploads}")
    private String uploadPath;

    /**
     * 保存商品
     */
    @PostMapping("/save")
    public String save(Product product,
                       @RequestParam(required = false) MultipartFile[] imageFiles) {
        try {
            // 处理图片上传
            if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                String imageUrl = uploadFile(imageFiles[0]);
                product.setMainImage(imageUrl);
            }
            
            product.setStatus(CommonConstant.PRODUCT_STATUS_ON);
            productService.add(product);
            log.info("商品添加成功: {}", product.getProductName());
            return "redirect:/admin/product/list?msg=添加成功";
        } catch (Exception e) {
            log.error("添加商品失败", e);
            return "redirect:/admin/product/add?error=" + e.getMessage();
        }
    }

    /**
     * 更新商品
     */
    @PostMapping("/update")
    public String update(Product product,
                         @RequestParam(required = false) MultipartFile[] imageFiles) {
        try {
            // 处理图片上传
            if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                String imageUrl = uploadFile(imageFiles[0]);
                product.setMainImage(imageUrl);
            }
            
            productService.update(product);
            log.info("商品更新成功: id={}", product.getId());
            return "redirect:/admin/product/list?msg=更新成功";
        } catch (Exception e) {
            log.error("更新商品失败", e);
            return "redirect:/admin/product/edit/" + product.getId() + "?error=" + e.getMessage();
        }
    }

    /**
     * 修改商品状态（上架/下架）
     */
    @GetMapping("/status/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable Integer status) {
        try {
            productService.updateStatus(id, status);
            String msg = status == 1 ? "上架成功" : "下架成功";
            log.info("商品状态更新: id={}, status={}", id, status);
            return "redirect:/admin/product/list?msg=" + msg;
        } catch (Exception e) {
            log.error("更新商品状态失败", e);
            return "redirect:/admin/product/list?error=" + e.getMessage();
        }
    }

    /**
     * 删除商品
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            log.info("商品删除成功: id={}", id);
            return "redirect:/admin/product/list?msg=删除成功";
        } catch (Exception e) {
            log.error("删除商品失败", e);
            return "redirect:/admin/product/list?error=" + e.getMessage();
        }
    }

    /**
     * 上传文件
     */
    private String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        
        // 创建上传目录
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString().replace("-", "") + suffix;
        
        // 保存文件
        File destFile = new File(uploadDir, newFilename);
        file.transferTo(destFile);
        
        // 返回访问路径
        return "/" + uploadPath + "/" + newFilename;
    }
}
