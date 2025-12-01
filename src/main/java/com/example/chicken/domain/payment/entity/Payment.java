package com.example.chicken.domain.payment.entity;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.entity.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Builder
	private Payment(User user, Order order, PaymentStatus paymentStatus) {
		this.user = user;
		this.order = order;
		this.status = paymentStatus;
	}

	public static Payment ready(User user, Order order) {
		return Payment.builder()
			.user(user)
			.order(order)
			.paymentStatus(PaymentStatus.READY)
			.build();
	}

	public void paid() {
		this.status = PaymentStatus.PAID;
	}
}
