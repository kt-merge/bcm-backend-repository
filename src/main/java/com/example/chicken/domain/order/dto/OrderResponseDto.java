package com.example.chicken.domain.order.dto;

import java.math.BigDecimal;

import com.example.chicken.domain.order.entity.Order;

public record OrderResponseDto(Long orderId, String productName, BigDecimal bidPrice) {

	public static OrderResponseDto from(Order order) {
		return new OrderResponseDto(order.getId(), order.getProduct().getName(), order.getFinalPrice());
	}

}
