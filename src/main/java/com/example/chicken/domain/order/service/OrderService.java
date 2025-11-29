package com.example.chicken.domain.order.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.ShippingInfoRequestDto;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.entity.ShippingInfo;
import com.example.chicken.domain.order.exception.OrderNotFoundException;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;

	@Transactional
	public Order createOrder(User user, Product product) {

		LocalDateTime expiredAt = LocalDateTime.now(ZoneOffset.UTC).plusDays(1);

		Order order = Order.builder()
			.finalPrice(product.getBidPrice())
			.expiredAt(expiredAt)
			.status(OrderStatus.PAYMENT_PENDING)
			.user(user)
			.product(product)
			.build();

		return this.orderRepository.save(order);
	}

	@Transactional
	public OrderResponseDto addShippingInfo(Long orderId, ShippingInfoRequestDto requestDto) {
		Order order = this.orderRepository.findById(orderId)
			.orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

		ShippingInfo shippingInfo = this.orderMapper.toShippingEntity(requestDto);

		order.addShippingInfo(shippingInfo);

		return this.orderMapper.toDto(order);
	}

	@Transactional(readOnly = true)
	public OrderResponseDto getOrder(Long orderId) {
		Order order = this.orderRepository.findById(orderId)
			.orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

		return this.orderMapper.toDto(order);
	}
}
