package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Product;
import com.wzc.common.mapper.ProductMapper;
import com.wzc.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public Page<Product> page(Integer pageNum, Integer pageSize, String productName) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(Product::getStatus, CommonConstant.PRODUCT_STATUS_DELETED);
        if (productName != null && !productName.trim().isEmpty()) {
            wrapper.like(Product::getProductName, productName);
        }
        wrapper.orderByDesc(Product::getCreateTime);
        return productMapper.selectPage(page, wrapper);
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public boolean add(Product product) {
        product.setStatus(CommonConstant.PRODUCT_STATUS_ON);
        int rows = productMapper.insert(product);
        log.info("新增商品: id={}, name={}", product.getId(), product.getProductName());
        return rows > 0;
    }

    @Override
    public boolean update(Product product) {
        int rows = productMapper.updateById(product);
        log.info("更新商品: id={}, name={}", product.getId(), product.getProductName());
        return rows > 0;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        int rows = productMapper.updateById(product);
        String statusDesc = status == CommonConstant.PRODUCT_STATUS_ON ? "上架" :
                status == CommonConstant.PRODUCT_STATUS_OFF ? "下架" : "删除";
        log.info("商品{}: id={}, status={}", statusDesc, id, status);
        return rows > 0;
    }

    @Override
    public boolean delete(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(CommonConstant.PRODUCT_STATUS_DELETED);
        int rows = productMapper.updateById(product);
        log.info("逻辑删除商品: id={}, affected={}", id, rows);
        return rows > 0;
    }
}