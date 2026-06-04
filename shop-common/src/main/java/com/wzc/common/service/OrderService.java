package com.wzc.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.Order;
import com.wzc.common.entity.OrderItem;

import java.util.List;

public interface OrderService {

    Order create(Long userId, List<Long> cartIds);

    Page<Order> pageByUserId(Integer pageNum, Integer pageSize, Long userId);

    Order getById(Long id);

    List<OrderItem> getItemsByOrderId(Long orderId);

    boolean pay(Long orderId);

    boolean ship(Long orderId);

    boolean complete(Long orderId);

    Page<Order> pageAll(Integer pageNum, Integer pageSize);
}
