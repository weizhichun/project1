package com.wzc.common.service;

import com.wzc.common.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> listByUserId(Long userId);

    boolean add(Long userId, Long productId, Integer quantity);

    boolean updateQuantity(Long id, Integer quantity);

    boolean delete(Long id);

    boolean deleteByUserAndProduct(Long userId, Long productId);

    boolean clear(Long userId);
}
