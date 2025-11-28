package com.example.chicken.domain.order.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

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

}
