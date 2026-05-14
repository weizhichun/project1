package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    // ==============================================
    // 演示1：基本变量输出 → 对应 templates/test/hello.html
    // ==============================================
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf！");
        model.addAttribute("name", "张三");
        model.addAttribute("age", 20);
        model.addAttribute("htmlContent", "<b>这是粗体文字</b>");
        model.addAttribute("now", new Date());
        
        return "test/hello";
    }

    // ==============================================
    // 演示2：字符串列表循环 → 对应 templates/test/list.html
    // ==============================================
    @GetMapping("/list")
    public String list(Model model) {
        List<String> fruits = Arrays.asList("苹果", "香蕉", "橙子", "葡萄", "芒果");
        model.addAttribute("fruits", fruits);
        
        return "test/list";
    }

    // ==============================================
    // 演示3：对象列表循环 → 对应 templates/test/users.html
    // ==============================================
    @GetMapping("/users")
    public String users(Model model) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "zhangsan", "123456", "张三", 20, "zhangsan@qq.com", 1, null, null));
        userList.add(new User(2L, "lisi", "123456", "李四", 21, "lisi@qq.com", 1, null, null));
        userList.add(new User(3L, "wangwu", "123456", "王五", 22, "wangwu@qq.com", 0, null, null));
        
        model.addAttribute("userList", userList);
        model.addAttribute("title", "模拟用户列表");
        
        return "test/users";
    }

    // ==============================================
    // 演示4：条件判断 → 对应 templates/test/condition.html
    // ==============================================
    @GetMapping("/condition")
    public String condition(Model model) {
        model.addAttribute("isAdmin", true);
        model.addAttribute("score", 85);
        model.addAttribute("status", "active");
        model.addAttribute("emptyList", new ArrayList<>());
        
        return "test/condition";
    }

    // ==============================================
    // 演示5：链接表达式 → 对应 templates/test/link.html
    // ==============================================
    @GetMapping("/link")
    public String testLink(Model model) {
        model.addAttribute("userId", 1L);
        return "test/link";
    }

    // ==============================================
    // 演示6：表单绑定（GET：显示表单）→ 对应 templates/test/form.html
    // ==============================================
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "test/form";
    }

    // ==============================================
    // 演示6：表单绑定（POST：处理表单提交）
    // ==============================================
    @PostMapping("/form")
    public String submitForm(@org.springframework.web.bind.annotation.ModelAttribute User user, Model model) {
        System.out.println("接收到的用户数据：" + user);
        model.addAttribute("user", user);
        model.addAttribute("successMsg", "表单提交成功！");
        return "test/form";
    }
}
