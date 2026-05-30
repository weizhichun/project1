package com.wzc.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzc.common.entity.Order;
import com.wzc.common.entity.OrderItem;
import com.wzc.common.entity.User;
import com.wzc.common.service.OrderService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResultUtil<Order> create(@RequestBody Map<String, Object> params, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        @SuppressWarnings("unchecked")
        List<Long> cartIds = (List<Long>) params.get("cartIds");
        if (cartIds == null || cartIds.isEmpty()) {
            return ResultUtil.fail("购物车为空");
        }
        Order order = orderService.create(user.getId(), cartIds);
        return ResultUtil.success(order);
    }

    @GetMapping("/page")
    public ResultUtil<Page<Order>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Page<Order> page = orderService.pageByUserId(pageNum, pageSize, user.getId());
        return ResultUtil.success(page);
    }

    @GetMapping("/{id}")
    public ResultUtil<Order> getById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Order order = orderService.getById(id);
        if (order == null) {
            return ResultUtil.fail("订单不存在");
        }
        if (!order.getUserId().equals(user.getId())) {
            return ResultUtil.fail("无权访问");
        }
        return ResultUtil.success(order);
    }

    @GetMapping("/{id}/items")
    public ResultUtil<List<OrderItem>> getItems(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Order order = orderService.getById(id);
        if (order == null) {
            return ResultUtil.fail("订单不存在");
        }
        if (!order.getUserId().equals(user.getId())) {
            return ResultUtil.fail("无权访问");
        }
        List<OrderItem> items = orderService.getItemsByOrderId(id);
        return ResultUtil.success(items);
    }

    @PostMapping("/{id}/pay")
    public ResultUtil<String> pay(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Order order = orderService.getById(id);
        if (order == null) {
            return ResultUtil.fail("订单不存在");
        }
        if (!order.getUserId().equals(user.getId())) {
            return ResultUtil.fail("无权操作");
        }
        boolean success = orderService.pay(id);
        if (success) {
            return ResultUtil.success("支付成功");
        }
        return ResultUtil.fail("支付失败");
    }

    @PostMapping("/{id}/complete")
    public ResultUtil<String> complete(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Order order = orderService.getById(id);
        if (order == null) {
            return ResultUtil.fail("订单不存在");
        }
        if (!order.getUserId().equals(user.getId())) {
            return ResultUtil.fail("无权操作");
        }
        boolean success = orderService.complete(id);
        if (success) {
            return ResultUtil.success("确认收货成功");
        }
        return ResultUtil.fail("确认收货失败");
    }
}
