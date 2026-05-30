CREATE DATABASE IF NOT EXISTS shop_web DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shop_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shop_web;

CREATE TABLE IF NOT EXISTS wzc_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(500) COMMENT '头像URL',
    status INT DEFAULT 1 COMMENT '状态 1正常 0禁用',
    is_deleted INT DEFAULT 0 COMMENT '逻辑删除 0未删除 1已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户表';

INSERT INTO wzc_user (username, password, nickname, phone, email, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', '13800000000', 'admin@example.com', 1),
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '普通用户', '13800000001', 'user@example.com', 1);

CREATE TABLE IF NOT EXISTS wzc_file_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) COMMENT '原始文件名',
    stored_name VARCHAR(255) COMMENT '存储文件名',
    file_path VARCHAR(500) COMMENT '文件相对路径',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_type VARCHAR(100) COMMENT '文件类型',
    md5 VARCHAR(64) COMMENT '文件MD5',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_md5 (md5)
) COMMENT '文件记录表';

CREATE TABLE IF NOT EXISTS wzc_cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT DEFAULT 1 COMMENT '数量',
    is_deleted INT DEFAULT 0 COMMENT '逻辑删除 0未删除 1已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_product_id (product_id)
) COMMENT '购物车表';

CREATE TABLE IF NOT EXISTS wzc_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(64) NOT NULL UNIQUE COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    status INT DEFAULT 0 COMMENT '订单状态 0待支付 1已支付 2已发货 3已完成',
    is_deleted INT DEFAULT 0 COMMENT '逻辑删除 0未删除 1已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_order_no (order_no)
) COMMENT '订单表';

CREATE TABLE IF NOT EXISTS wzc_order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    quantity INT DEFAULT 1 COMMENT '数量',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    main_image VARCHAR(500) COMMENT '商品主图',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
) COMMENT '订单项表';

USE shop_admin;

CREATE TABLE IF NOT EXISTS wzc_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock INT DEFAULT 0 COMMENT '库存',
    category VARCHAR(100) COMMENT '分类',
    description TEXT COMMENT '商品描述',
    main_image VARCHAR(500) COMMENT '主图URL',
    images TEXT COMMENT '商品图片JSON数组',
    status INT DEFAULT 1 COMMENT '状态 1上架 0下架 -1删除',
    is_deleted INT DEFAULT 0 COMMENT '逻辑删除 0未删除 1已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '商品表';

INSERT INTO wzc_product (product_name, price, stock, category, description, status) VALUES
('iPhone 15 Pro', 8999.00, 100, '手机', 'Apple iPhone 15 Pro 256GB 深空黑色', 1),
('MacBook Pro 14', 14999.00, 50, '电脑', 'Apple MacBook Pro 14英寸 M3芯片', 1),
('AirPods Pro 2', 1899.00, 200, '配件', 'Apple AirPods Pro 2代 主动降噪', 1),
('iPad Air', 4799.00, 80, '平板', 'Apple iPad Air 10.9英寸', 1),
('Apple Watch Series 9', 2999.00, 150, '手表', 'Apple Watch Series 9 GPS款', 1);

CREATE TABLE IF NOT EXISTS wzc_file_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) COMMENT '原始文件名',
    stored_name VARCHAR(255) COMMENT '存储文件名',
    file_path VARCHAR(500) COMMENT '文件相对路径',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_type VARCHAR(100) COMMENT '文件类型',
    md5 VARCHAR(64) COMMENT '文件MD5',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_md5 (md5)
) COMMENT '文件记录表';
