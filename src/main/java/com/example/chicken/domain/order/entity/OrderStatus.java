package com.example.chicken.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
	PAYMENT_PENDING("결제대기"),
	PAID("결제완료"),
	EXPIRED("결제기한 만료"),
	CANCELLED("주문취소");

	private final String description;
}
