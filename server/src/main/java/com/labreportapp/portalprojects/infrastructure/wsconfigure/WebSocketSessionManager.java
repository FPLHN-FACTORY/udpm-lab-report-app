package com.labreportapp.portalprojects.infrastructure.wsconfigure;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<String, SessionWebSocketInfo> sessionInfoMap = new ConcurrentHashMap<>();

    public void storeSessionInfo(String sessionId, String jwtToken, String name, String userName, String email, String id) {
        SessionWebSocketInfo sessionInfo = new SessionWebSocketInfo(jwtToken, name, userName, email, id);
        sessionInfoMap.put(sessionId, sessionInfo);
    }

    public SessionWebSocketInfo getSessionInfo(String sessionId) {
        return sessionInfoMap.get(sessionId);
    }

    public void removeSessionInfo(String sessionId) {
        sessionInfoMap.remove(sessionId);
    }
}

