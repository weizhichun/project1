package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.Cart;
import com.wzc.common.entity.Order;
import com.wzc.common.entity.OrderItem;
import com.wzc.common.entity.Product;
import com.wzc.common.exception.BusinessException;
import com.wzc.common.mapper.CartMapper;
import com.wzc.common.mapper.OrderItemMapper;
import com.wzc.common.mapper.OrderMapper;
import com.wzc.common.mapper.ProductMapper;
import com.wzc.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order create(Long userId, List<Long> cartIds) {
        List<Cart> cartList = cartMapper.selectBatchIds(cartIds);
        if (cartList.isEmpty()) {
            throw new BusinessException("购物车为空");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Cart cart : cartList) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null) {
                throw new BusinessException("商品不存在");
            }
            if (product.getStock() == null || product.getStock() < cart.getQuantity()) {
                throw new BusinessException("商品库存不足: " + product.getProductName());
            }

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setProductName(product.getProductName());
            item.setPrice(product.getPrice());
            item.setQuantity(cart.getQuantity());
            item.setMainImage(product.getMainImage());
            item.setTotalAmount(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            orderItems.add(item);

            totalAmount = totalAmount.add(item.getTotalAmount());

            product.setStock(product.getStock() - cart.getQuantity());
            productMapper.updateById(product);
        }

        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(CommonConstant.ORDER_STATUS_PENDING);
        orderMapper.insert(order);

        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());
            orderItemMapper.insert(item);
        }

        cartMapper.deleteBatchIds(cartIds);

        log.info("订单创建: orderId={}, orderNo={}, totalAmount={}", 
                 order.getId(), order.getOrderNo(), totalAmount);
        return order;
    }

    @Override
    public Page<Order> pageByUserId(Integer pageNum, Integer pageSize, Long userId) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreateTime);
        return orderMapper.selectPage(page, wrapper);
    }

    @Override
    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItem::getOrderId, orderId);
        return orderItemMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pay(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != CommonConstant.ORDER_STATUS_PENDING) {
            throw new BusinessException("订单状态不正确");
        }
        order.setStatus(CommonConstant.ORDER_STATUS_PAID);
        int rows = orderMapper.updateById(order);
        log.info("订单支付: orderId={}", orderId);
        return rows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ship(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != CommonConstant.ORDER_STATUS_PAID) {
            throw new BusinessException("订单状态不正确");
        }
        order.setStatus(CommonConstant.ORDER_STATUS_SHIPPED);
        int rows = orderMapper.updateById(order);
        log.info("订单发货: orderId={}", orderId);
        return rows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean complete(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != CommonConstant.ORDER_STATUS_SHIPPED) {
            throw new BusinessException("订单状态不正确");
        }
        order.setStatus(CommonConstant.ORDER_STATUS_COMPLETED);
        int rows = orderMapper.updateById(order);
        log.info("订单完成: orderId={}", orderId);
        return rows > 0;
    }

    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "ORD" + timestamp + uuid;
    }
}
