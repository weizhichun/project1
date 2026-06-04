let currentSessionId = null;

async function createSession() {
    const res = await fetch('/api/chat/session/create', { method: 'POST' });
    const data = await res.json();
    if (data.code === 200) {
        currentSessionId = data.data.id;
        loadSessions();
        document.getElementById('chatMessages').innerHTML = '';
        document.getElementById('chatMessages').style.display = 'block';
        document.getElementById('welcomeTip').style.display = 'none';
    } else {
        alert(data.msg);
    }
}

async function loadSessions() {
    const res = await fetch('/api/chat/sessions');
    const data = await res.json();
    if (data.code === 200) {
        const list = document.getElementById('sessionList');
        if (data.data.length === 0) {
            list.innerHTML = '<div class="session-item" style="color:#999;text-align:center;">暂无对话记录</div>';
        } else {
            list.innerHTML = data.data.map(s => `
                <div class="session-item ${s.id === currentSessionId ? 'active' : ''}"
                     onclick="selectSession(${s.id})">
                    ${s.title || '新对话'}
                </div>
            `).join('');
        }
    }
}

async function selectSession(sessionId) {
    currentSessionId = sessionId;
    loadSessions();
    document.getElementById('chatMessages').style.display = 'block';
    document.getElementById('welcomeTip').style.display = 'none';
    await loadMessages();
}

async function loadMessages() {
    if (!currentSessionId) return;
    const res = await fetch(`/api/chat/messages/${currentSessionId}`);
    const data = await res.json();
    if (data.code === 200) {
        const container = document.getElementById('chatMessages');
        if (data.data.length === 0) {
            container.innerHTML = '<div style="text-align:center;color:#999;margin-top:50px;">开始对话吧</div>';
        } else {
            container.innerHTML = data.data.map(m => `
                <div class="message ${m.role}">
                    <strong>${m.role === 'user' ? '我' : 'AI客服'}:</strong>
                    <p>${escapeHtml(m.content)}</p>
                </div>
            `).join('');
        }
        container.scrollTop = container.scrollHeight;
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

async function sendMessage() {
    if (!currentSessionId) {
        await createSession();
    }
    const input = document.getElementById('messageInput');
    const content = input.value.trim();
    if (!content) return;

    const res = await fetch('/api/chat/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ sessionId: currentSessionId, content })
    });
    const data = await res.json();
    if (data.code === 200) {
        input.value = '';
        await loadMessages();
    } else {
        alert(data.msg);
    }
}

document.getElementById('messageInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
});

loadSessions();