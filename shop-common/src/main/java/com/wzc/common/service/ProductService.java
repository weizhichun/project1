package com.wzc.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.Product;

public interface ProductService {

    Page<Product> page(Integer pageNum, Integer pageSize, String productName);

    Product getById(Long id);

    boolean add(Product product);

    boolean update(Product product);

    boolean updateStatus(Long id, Integer status);

    boolean delete(Long id);
}