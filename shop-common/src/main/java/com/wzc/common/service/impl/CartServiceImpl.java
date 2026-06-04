package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wzc.common.entity.Cart;
import com.wzc.common.entity.Product;
import com.wzc.common.exception.BusinessException;
import com.wzc.common.mapper.CartMapper;
import com.wzc.common.mapper.ProductMapper;
import com.wzc.common.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Override
    public List<Cart> listByUserId(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .orderByDesc(Cart::getCreateTime);
        return cartMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Long userId, Long productId, Integer quantity) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (product.getStock() == null || product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }

        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);
        Cart existCart = cartMapper.selectOne(wrapper);

        if (existCart != null) {
            existCart.setQuantity(existCart.getQuantity() + quantity);
            int rows = cartMapper.updateById(existCart);
            log.info("购物车数量更新: userId={}, productId={}, quantity={}", userId, productId, existCart.getQuantity());
            return rows > 0;
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            int rows = cartMapper.insert(cart);
            log.info("购物车添加: userId={}, productId={}, quantity={}", userId, productId, quantity);
            return rows > 0;
        }
    }

    @Override
    public boolean updateQuantity(Long userId, Long id, Integer quantity) {
        Cart cart = cartMapper.selectById(id);
        if (cart == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        if (!cart.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该购物车项");
        }
        
        Product product = productMapper.selectById(cart.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (product.getStock() == null || product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        cart.setQuantity(quantity);
        int rows = cartMapper.updateById(cart);
        log.info("购物车数量更新: userId={}, id={}, quantity={}", userId, id, quantity);
        return rows > 0;
    }

    @Override
    public boolean delete(Long userId, Long id) {
        Cart cart = cartMapper.selectById(id);
        if (cart == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        if (!cart.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该购物车项");
        }
        
        int rows = cartMapper.deleteById(id);
        log.info("购物车删除: userId={}, id={}", userId, id);
        return rows > 0;
    }

    @Override
    public boolean deleteByUserAndProduct(Long userId, Long productId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);
        int rows = cartMapper.delete(wrapper);
        log.info("购物车删除: userId={}, productId={}", userId, productId);
        return rows > 0;
    }

    @Override
    public boolean clear(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        int rows = cartMapper.delete(wrapper);
        log.info("购物车清空: userId={}", userId);
        return rows > 0;
    }
}
