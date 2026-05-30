package com.wzc.admin.config;

import com.wzc.common.entity.Product;
import com.wzc.common.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminDataInitializer implements CommandLineRunner {

    private final ProductMapper productMapper;

    @Override
    public void run(String... args) {
        log.info("开始初始化商品数据...");

        // 只初始化商品
        if (productMapper.selectCount(null) == 0) {
            Product p1 = new Product();
            p1.setProductName("iPhone 15 Pro");
            p1.setPrice(new BigDecimal("8999.00"));
            p1.setStock(100);
            p1.setCategory("手机");
            p1.setDescription("Apple iPhone 15 Pro 256GB 深空黑色");
            p1.setStatus(1);
            p1.setCreateTime(LocalDateTime.now());
            p1.setUpdateTime(LocalDateTime.now());
            productMapper.insert(p1);

            Product p2 = new Product();
            p2.setProductName("MacBook Pro 14");
            p2.setPrice(new BigDecimal("14999.00"));
            p2.setStock(50);
            p2.setCategory("电脑");
            p2.setDescription("Apple MacBook Pro 14英寸 M3芯片");
            p2.setStatus(1);
            p2.setCreateTime(LocalDateTime.now());
            p2.setUpdateTime(LocalDateTime.now());
            productMapper.insert(p2);

            Product p3 = new Product();
            p3.setProductName("AirPods Pro 2");
            p3.setPrice(new BigDecimal("1899.00"));
            p3.setStock(200);
            p3.setCategory("配件");
            p3.setDescription("Apple AirPods Pro 2代 主动降噪");
            p3.setStatus(1);
            p3.setCreateTime(LocalDateTime.now());
            p3.setUpdateTime(LocalDateTime.now());
            productMapper.insert(p3);

            Product p4 = new Product();
            p4.setProductName("iPad Air");
            p4.setPrice(new BigDecimal("4799.00"));
            p4.setStock(80);
            p4.setCategory("平板");
            p4.setDescription("Apple iPad Air 10.9英寸");
            p4.setStatus(1);
            p4.setCreateTime(LocalDateTime.now());
            p4.setUpdateTime(LocalDateTime.now());
            productMapper.insert(p4);

            Product p5 = new Product();
            p5.setProductName("Apple Watch Series 9");
            p5.setPrice(new BigDecimal("2999.00"));
            p5.setStock(150);
            p5.setCategory("手表");
            p5.setDescription("Apple Watch Series 9 GPS款");
            p5.setStatus(1);
            p5.setCreateTime(LocalDateTime.now());
            p5.setUpdateTime(LocalDateTime.now());
            productMapper.insert(p5);

            log.info("商品数据初始化完成！共5个商品");
        } else {
            log.info("商品数据已存在，跳过初始化");
        }
    }
}
