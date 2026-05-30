# 在线商城系统 - Java Web整合实验项目

## 项目概述

本项目是一个基于 Spring Boot + Thymeleaf + MySQL 的在线商城系统，实现了用户管理、商品浏览、购物车、订单管理等核心功能。项目遵循分层架构设计，代码规范，功能完整。

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **前端模板**: Thymeleaf
- **数据库**: MySQL 8.0+
- **持久层**: MyBatis-Plus 3.5.3.1
- **加密**: Spring Security BCrypt
- **构建工具**: Maven

## 项目结构

```
wei-zhichun/
├── shop-common/           # 公共模块（实体类、Mapper、Service、配置等）
│   └── src/main/java/com/wzc/common/
│       ├── config/       # 配置类
│       ├── constant/     # 常量
│       ├── entity/       # 实体类
│       ├── mapper/       # MyBatis-Plus Mapper
│       ├── service/      # 业务逻辑接口及实现
│       └── utils/        # 工具类
├── shop-web/             # 前台用户系统（端口 8080）
│   └── src/main/
│       ├── java/com/wzc/web/
│       │   └── controller/  # 控制器
│       └── resources/
│           ├── templates/   # Thymeleaf 模板
│           └── application.yml
└── shop-admin/           # 后台管理系统（端口 8081，已有基础框架）
```

## 核心功能

### 1. 用户模块
- 用户注册（密码BCrypt加密）
- 用户登录（Session管理）
- 个人信息修改
- 头像上传

### 2. 商品模块
- 商品列表展示（分页）
- 商品搜索
- 商品详情
- 库存管理

### 3. 购物车模块
- 商品加入购物车
- 购物车数量修改
- 商品移除
- 购物车清空

### 4. 订单模块
- 从购物车创建订单
- 订单支付
- 订单发货
- 确认收货
- 订单列表及详情

### 5. 文件上传
- 图片上传（支持多种格式）
- 文件大小限制
- 图片展示

## 快速开始

### 环境要求
- JDK 8+
- MySQL 8.0+
- Maven 3.6+

### 1. 数据库初始化

执行项目根目录下的 `init.sql` 文件：

```bash
# 在 MySQL 命令行或工具中执行
source /path/to/wei-zhichun/init.sql
```

初始化内容：
- 创建 `shop_web` 数据库（前台）
- 创建 `shop_admin` 数据库（后台）
- 建表：用户、商品、购物车、订单、订单项、文件记录
- 预置测试数据：
  - 测试用户：admin/123456
  - 测试商品：iPhone、MacBook、AirPods 等

### 2. 配置修改

修改 `shop-web/src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shop_web?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root          # 改为你的MySQL用户名
    password: your_password # 改为你的MySQL密码
```

### 3. 编译运行

```bash
# 在项目根目录编译
mvn clean install

# 运行前台系统
cd shop-web
mvn spring-boot:run

# 或直接执行jar包
java -jar shop-web/target/shop-web.jar
```

### 4. 访问系统

启动成功后，访问：

- 前台首页：http://localhost:8080/
- 登录页面：http://localhost:8080/login
- 注册页面：http://localhost:8080/register

## API接口文档

### 用户相关

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /web/user/register | 用户注册 |
| POST | /web/user/login | 用户登录 |
| POST | /web/user/logout | 用户退出 |
| GET | /web/user/session | 获取当前登录用户 |
| PUT | /web/user/profile | 修改个人信息 |
| PUT | /web/user/avatar | 修改头像 |

### 商品相关

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /web/product/page | 商品列表分页 |
| GET | /web/product/{id} | 商品详情 |

### 购物车相关

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /web/cart/list | 获取购物车列表 |
| POST | /web/cart/add | 加入购物车 |
| PUT | /web/cart/update | 修改数量 |
| DELETE | /web/cart/{id} | 删除购物车项 |
| DELETE | /web/cart/clear | 清空购物车 |

### 订单相关

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /web/order/create | 创建订单 |
| GET | /web/order/page | 订单列表分页 |
| GET | /web/order/{id} | 订单详情 |
| GET | /web/order/{id}/items | 订单商品列表 |
| POST | /web/order/{id}/pay | 订单支付 |
| POST | /web/order/{id}/complete | 确认收货 |

## 数据库表结构

### wzc_user（用户表）
- id: 主键
- username: 用户名（唯一）
- password: 密码（BCrypt加密）
- nickname: 昵称
- phone: 手机号
- email: 邮箱
- avatar: 头像URL
- status: 状态（1正常，0禁用）
- is_deleted: 逻辑删除
- create_time, update_time: 时间戳

### wzc_product（商品表）
- id: 主键
- product_name: 商品名称
- price: 价格
- stock: 库存
- category: 分类
- description: 描述
- main_image: 主图
- images: 图片数组
- status: 状态（1上架，0下架）
- is_deleted: 逻辑删除
- 时间戳字段

### wzc_cart（购物车表）
- id: 主键
- user_id: 用户ID
- product_id: 商品ID
- quantity: 数量
- is_deleted: 逻辑删除
- 时间戳字段

### wzc_order（订单表）
- id: 主键
- order_no: 订单号（唯一）
- user_id: 用户ID
- total_amount: 总金额
- status: 状态（0待支付，1已支付，2已发货，3已完成）
- is_deleted: 逻辑删除
- 时间戳字段

### wzc_order_item（订单项表）
- id: 主键
- order_id: 订单ID
- product_id: 商品ID
- product_name: 商品名称
- price: 价格
- quantity: 数量
- total_amount: 小计
- main_image: 主图

### wzc_file_record（文件记录表）
- id: 主键
- original_name: 原始文件名
- stored_name: 存储文件名
- file_path: 文件路径
- file_size: 文件大小
- file_type: 文件类型
- md5: 文件MD5（去重用）
- create_time: 创建时间

## 架构设计

### 分层架构
1. **Controller层**: 接收请求，参数校验，调用Service，返回结果
2. **Service层**: 业务逻辑处理，事务控制，调用Mapper
3. **Mapper层**: 数据库操作（基于MyBatis-Plus）
4. **Entity层**: 数据实体（使用Lombok简化代码）

### 安全设计
- 密码使用BCrypt加密存储
- Session管理登录状态
- 统一异常处理，不暴露敏感信息
- SQL注入防护（MyBatis-Plus）

### 代码规范
- 阿里巴巴Java开发手册
- 统一返回结果 `ResultUtil`
- 统一异常处理 `GlobalExceptionHandler`
- 常量集中管理 `CommonConstant`
- 逻辑删除而非物理删除

## 测试账号

| 用户名 | 密码 | 说明 |
|--------|------|------|
| admin | 123456 | 预置管理员账号 |
| user | 123456 | 普通用户账号 |

## 常见问题

1. **数据库连接失败**: 检查application-dev.yml中的数据库配置
2. **中文乱码**: 确保数据库和表的字符集为utf8mb4
3. **端口冲突**: 修改application.yml中的端口配置
4. **文件上传失败**: 检查uploads目录权限

## 开发说明

### 运行后台管理系统
```bash
cd shop-admin
mvn spring-boot:run
```
访问：http://localhost:8081

### 项目规范
- 提交前请执行 `mvn clean compile` 确保无编译错误
- 新增功能请遵循现有代码风格
- Service层添加单元测试

## 参考资料

- [Spring Boot官方文档](https://spring.io/projects/spring-boot)
- [MyBatis-Plus文档](https://baomidou.com/)
- [Thymeleaf文档](https://www.thymeleaf.org/)
- [阿里巴巴Java开发手册](https://github.com/alibaba/p3c)

## 许可证

MIT License
