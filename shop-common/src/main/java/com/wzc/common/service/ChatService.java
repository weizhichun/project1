package com.wzc.common.service;

import com.wzc.common.entity.ChatMessage;
import com.wzc.common.entity.ChatSession;
import java.util.List;

public interface ChatService {
    ChatSession createSession(Long userId);
    List<ChatSession> getSessionsByUserId(Long userId);
    ChatMessage sendMessage(Long sessionId, String content);
    List<ChatMessage> getMessagesBySessionId(Long sessionId);
}
