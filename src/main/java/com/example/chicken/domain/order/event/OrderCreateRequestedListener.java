package com.example.chicken.domain.order.event;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.exception.ProductNotFoundException;
import com.example.chicken.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCreateRequestedListener {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	@Transactional(propagation = REQUIRES_NEW)
	@TransactionalEventListener(phase = AFTER_COMMIT)
	public void handle(OrderCreateRequestedEvent event) {
		Long userId = event.userId();

		User user = this.userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId.toString()));

		Long productId = event.productId();

		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId.toString()));

		LocalDateTime expiredAt = LocalDateTime.now(ZoneOffset.UTC).plusDays(1);

		Order order = Order.builder()
			.finalPrice(product.getBidPrice())
			.expiredAt(expiredAt)
			.status(OrderStatus.PAYMENT_PENDING)
			.user(user)
			.product(product)
			.build();

		this.orderRepository.save(order);
	}
}
