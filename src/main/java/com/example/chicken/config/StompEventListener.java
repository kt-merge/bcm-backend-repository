package com.example.chicken.config;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StompEventListener {
	private final Set<String> sessions = ConcurrentHashMap.newKeySet();

	@EventListener
	public void connectHandle(SessionConnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		sessions.add(accessor.getSessionId());

		log.info("New WebSocket connection, sessionId: {}, session Count: {}", accessor.getSessionId(), sessions.size());
	}

	@EventListener
	public void disconnectHandle(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		sessions.remove(accessor.getSessionId());

		log.info("WebSocket disconnected, sessionId: {}, session Count: {}", accessor.getSessionId(), sessions.size());
	}
}
