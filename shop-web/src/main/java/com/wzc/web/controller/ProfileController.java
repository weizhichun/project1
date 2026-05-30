package com.wzc.web.controller;

import com.wzc.common.entity.User;
import com.wzc.common.service.FileService;
import com.wzc.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 个人中心控制器
 */
@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final FileService fileService;

    /**
     * 显示个人中心页面
     */
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        User fullUser = userService.getById(user.getId());
        model.addAttribute("user", fullUser);
        return "profile";
    }
}
