CREATE DATABASE IF NOT EXISTS shop_web DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shop_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shop_web;

CREATE TABLE IF NOT EXISTS wzc_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
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
('wzc', '123456', 'wzc', '13800000000', 'wzc@example.com', 1);

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