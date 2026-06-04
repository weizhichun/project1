package com.wzc.web.controller;

import com.wzc.common.entity.Cart;
import com.wzc.common.entity.User;
import com.wzc.common.service.CartService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public ResultUtil<List<Cart>> list(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        List<Cart> cartList = cartService.listByUserId(user.getId());
        return ResultUtil.success(cartList);
    }

    @PostMapping("/add")
    public ResultUtil<String> add(@RequestBody Map<String, Object> params, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Object productIdObj = params.get("productId");
        Object quantityObj = params.get("quantity");
        if (productIdObj == null) {
            return ResultUtil.fail("商品ID不能为空");
        }
        Long productId = Long.valueOf(productIdObj.toString());
        Integer quantity = quantityObj != null ? Integer.valueOf(quantityObj.toString()) : 1;
        boolean success = cartService.add(user.getId(), productId, quantity);
        if (success) {
            return ResultUtil.success("添加成功");
        }
        return ResultUtil.fail("添加失败");
    }

    @PutMapping("/update")
    public ResultUtil<String> update(@RequestBody Map<String, Object> params, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        Object idObj = params.get("id");
        Object quantityObj = params.get("quantity");
        if (idObj == null || quantityObj == null) {
            return ResultUtil.fail("参数不完整");
        }
        Long id = Long.valueOf(idObj.toString());
        Integer quantity = Integer.valueOf(quantityObj.toString());
        boolean success = cartService.updateQuantity(user.getId(), id, quantity);
        if (success) {
            return ResultUtil.success("更新成功");
        }
        return ResultUtil.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public ResultUtil<String> delete(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        boolean success = cartService.delete(user.getId(), id);
        if (success) {
            return ResultUtil.success("删除成功");
        }
        return ResultUtil.fail("删除失败");
    }

    @DeleteMapping("/clear")
    public ResultUtil<String> clear(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("未登录");
        }
        boolean success = cartService.clear(user.getId());
        if (success) {
            return ResultUtil.success("清空成功");
        }
        return ResultUtil.fail("清空失败");
    }
}
