package com.example.chicken.domain.order.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.service.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final ProductMapper productMapper;

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

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Order order = this.orderRepository.findByIdAndUser(orderId, email)
			.orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

		ProductResponseDto productResponse = productMapper.toResponseDto(order.getProduct());

		return this.orderMapper.toDto(order, productResponse);
	}

}
