package com.example.chicken.domain.order.event;

public record OrderCreateRequestedEvent(Long userId, Long productId) {

	public static OrderCreateRequestedEvent of(Long userId, Long productId) {
		return new OrderCreateRequestedEvent(userId, productId);
	}

}
