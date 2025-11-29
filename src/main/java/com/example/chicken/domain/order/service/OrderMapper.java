package com.example.chicken.domain.order.service;

import org.springframework.stereotype.Component;

import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.entity.Order;

@Component
public class OrderMapper {

	public OrderResponseDto toDto(Order order) {
		return OrderResponseDto.from(order);
	}
}
