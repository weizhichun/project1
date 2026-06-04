package com.wzc.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.Order;
import com.wzc.common.service.OrderService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员订单管理控制器
 */
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class OrderManagementController {
    private final OrderService orderService;

    /**
     * 分页获取所有订单列表
     */
    @GetMapping("/list")
    public ResultUtil<Page<Order>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Order> result = orderService.pageAll(page, size);
        return ResultUtil.success(result);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{id}")
    public ResultUtil<Order> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResultUtil.success(order);
    }

    /**
     * 订单发货
     */
    @PostMapping("/ship/{id}")
    public ResultUtil<String> ship(@PathVariable Long id) {
        boolean success = orderService.ship(id);
        return success ? ResultUtil.success("发货成功") : ResultUtil.fail("发货失败");
    }

    /**
     * 订单完成
     */
    @PostMapping("/complete/{id}")
    public ResultUtil<String> complete(@PathVariable Long id) {
        boolean success = orderService.complete(id);
        return success ? ResultUtil.success("操作成功") : ResultUtil.fail("操作失败");
    }
}
