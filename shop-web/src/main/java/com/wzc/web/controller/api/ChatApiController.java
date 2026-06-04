package com.wzc.web.controller.api;

import com.wzc.common.entity.ChatMessage;
import com.wzc.common.entity.ChatSession;
import com.wzc.common.entity.User;
import com.wzc.common.service.ChatService;
import com.wzc.common.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatApiController {
    private final ChatService chatService;

    @PostMapping("/session/create")
    public ResultUtil<ChatSession> createSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("请先登录");
        }
        ChatSession chatSession = chatService.createSession(user.getId());
        return ResultUtil.success(chatSession);
    }

    @GetMapping("/sessions")
    public ResultUtil<List<ChatSession>> getSessions(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("请先登录");
        }
        List<ChatSession> sessions = chatService.getSessionsByUserId(user.getId());
        return ResultUtil.success(sessions);
    }

    @PostMapping("/send")
    public ResultUtil<ChatMessage> sendMessage(@RequestBody Map<String, Object> params,
                                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("请先登录");
        }
        Long sessionId = Long.valueOf(params.get("sessionId").toString());
        String content = (String) params.get("content");
        if (content == null || content.trim().isEmpty()) {
            return ResultUtil.fail("消息内容不能为空");
        }
        ChatMessage message = chatService.sendMessage(sessionId, content);
        return ResultUtil.success(message);
    }

    @GetMapping("/messages/{sessionId}")
    public ResultUtil<List<ChatMessage>> getMessages(@PathVariable Long sessionId,
                                                     HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.fail("请先登录");
        }
        List<ChatMessage> messages = chatService.getMessagesBySessionId(sessionId);
        return ResultUtil.success(messages);
    }
}
