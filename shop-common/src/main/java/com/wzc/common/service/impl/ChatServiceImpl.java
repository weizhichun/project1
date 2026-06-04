package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzc.common.entity.ChatMessage;
import com.wzc.common.entity.ChatSession;
import com.wzc.common.mapper.ChatMessageMapper;
import com.wzc.common.mapper.ChatSessionMapper;
import com.wzc.common.service.ChatService;
import com.wzc.common.utils.AiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatSessionMapper sessionMapper;
    private final ChatMessageMapper messageMapper;
    private final AiClient aiClient;

    @Override
    @Transactional
    public ChatSession createSession(Long userId) {
        ChatSession session = new ChatSession();
        session.setUserId(userId);
        session.setSessionNo(UUID.randomUUID().toString().replace("-", ""));
        session.setTitle("新对话");
        session.setStatus(1);
        sessionMapper.insert(session);
        return session;
    }

    @Override
    public List<ChatSession> getSessionsByUserId(Long userId) {
        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getUserId, userId)
               .eq(ChatSession::getIsDeleted, 0)
               .orderByDesc(ChatSession::getCreateTime);
        return sessionMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public ChatMessage sendMessage(Long sessionId, String content) {
        ChatMessage userMessage = new ChatMessage();
        userMessage.setSessionId(sessionId);
        userMessage.setRole("user");
        userMessage.setContent(content);
        messageMapper.insert(userMessage);

        String aiResponse = aiClient.chat(content);

        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setSessionId(sessionId);
        aiMessage.setRole("assistant");
        aiMessage.setContent(aiResponse);
        messageMapper.insert(aiMessage);

        return aiMessage;
    }

    @Override
    public List<ChatMessage> getMessagesBySessionId(Long sessionId) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getSessionId, sessionId)
               .eq(ChatMessage::getIsDeleted, 0)
               .orderByAsc(ChatMessage::getCreateTime);
        return messageMapper.selectList(wrapper);
    }
}
