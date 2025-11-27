package com.example.chicken.domain.order;

import java.math.BigDecimal;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.User;
import com.example.chicken.domain.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JoinColumn(name = "product_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@Builder
	private Order(long id, BigDecimal amount, OrderStatus orderStatus, User user, Product product) {
		this.id = id;
		this.amount = amount;
		this.orderStatus = orderStatus;
		this.user = user;
		this.product = product;
	}

}
