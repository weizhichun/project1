package com.wzc.common.config;

import com.wzc.common.entity.*;
import com.wzc.common.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final FileRecordMapper fileRecordMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        try {
            log.info("=== 开始初始化数据库 ===");

            // 初始化用户
            long userCount = 0;
            try {
                userCount = userMapper.selectCount(null);
            } catch (Exception e) {
                log.error("查询用户数失败: {}", e.getMessage());
            }
            if (userCount == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setNickname("管理员");
                admin.setPhone("13800000000");
                admin.setEmail("admin@example.com");
                admin.setStatus(1);
                admin.setCreateTime(LocalDateTime.now());
                admin.setUpdateTime(LocalDateTime.now());
                userMapper.insert(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setNickname("普通用户");
                user.setPhone("13800000001");
                user.setEmail("user@example.com");
                user.setStatus(1);
                user.setCreateTime(LocalDateTime.now());
                user.setUpdateTime(LocalDateTime.now());
                userMapper.insert(user);

                log.info("✅ 用户数据初始化完成，插入2个用户！");
            } else {
                log.info("用户数据已存在，跳过初始化: {}个", userCount);
            }

            // 初始化商品
            long productCount = 0;
            try {
                productCount = productMapper.selectCount(null);
            } catch (Exception e) {
                log.error("查询商品数失败: {}", e.getMessage());
            }
            if (productCount == 0) {
                Product p1 = new Product();
                p1.setProductName("iPhone 15 Pro");
                p1.setPrice(new BigDecimal("8999.00"));
                p1.setStock(100);
                p1.setCategory("手机");
                p1.setDescription("Apple iPhone 15 Pro 256GB 深空黑色");
                p1.setStatus(1);
                productMapper.insert(p1);

                Product p2 = new Product();
                p2.setProductName("MacBook Pro 14");
                p2.setPrice(new BigDecimal("14999.00"));
                p2.setStock(50);
                p2.setCategory("电脑");
                p2.setDescription("Apple MacBook Pro 14英寸 M3芯片");
                p2.setStatus(1);
                productMapper.insert(p2);

                Product p3 = new Product();
                p3.setProductName("AirPods Pro 2");
                p3.setPrice(new BigDecimal("1899.00"));
                p3.setStock(200);
                p3.setCategory("配件");
                p3.setDescription("Apple AirPods Pro 2代 主动降噪");
                p3.setStatus(1);
                productMapper.insert(p3);

                Product p4 = new Product();
                p4.setProductName("iPad Air");
                p4.setPrice(new BigDecimal("4799.00"));
                p4.setStock(80);
                p4.setCategory("平板");
                p4.setDescription("Apple iPad Air 10.9英寸");
                p4.setStatus(1);
                productMapper.insert(p4);

                Product p5 = new Product();
                p5.setProductName("Apple Watch Series 9");
                p5.setPrice(new BigDecimal("2999.00"));
                p5.setStock(150);
                p5.setCategory("手表");
                p5.setDescription("Apple Watch Series 9 GPS款");
                p5.setStatus(1);
                productMapper.insert(p5);

                log.info("✅ 商品数据初始化完成，插入5个商品！");
            } else {
                log.info("商品数据已存在，跳过初始化: {}个", productCount);
            }

            log.info("=== 数据库初始化完成 ===");
        } catch (Exception e) {
            log.error("❌ 数据库初始化失败: {}", e.getMessage(), e);
        }
    }
}
