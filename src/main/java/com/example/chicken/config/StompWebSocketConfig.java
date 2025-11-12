package com.example.chicken.config;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/connect")
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}

}
