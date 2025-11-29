package com.example.chicken.domain.order.service;

import org.springframework.stereotype.Component;

import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.ShippingInfoRequestDto;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.ShippingInfo;
import com.example.chicken.domain.product.dto.ProductResponseDto;

@Component
public class OrderMapper {

	public OrderResponseDto toDto(Order order) {
		return OrderResponseDto.from(order);
	}

	public OrderResponseDto toDto(Order order, ProductResponseDto product) {
		return OrderResponseDto.of(order, product);
	}

	public ShippingInfo toShippingEntity(ShippingInfoRequestDto requestDto) {
		return ShippingInfo.builder()
			.name(requestDto.name())
			.phoneNumber(requestDto.phoneNumber())
			.zipCode(requestDto.zipCode())
			.address(requestDto.address())
			.detailAddress(requestDto.detailAddress())
			.build();
	}

}
