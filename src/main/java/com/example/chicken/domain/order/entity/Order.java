package com.example.chicken.domain.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.Product;

import jakarta.persistence.Embedded;
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

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private BigDecimal finalPrice;

	private LocalDateTime expiredAt;

	@Embedded
	private ShippingInfo shippingInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Builder
	private Order(OrderStatus status,
				  BigDecimal finalPrice,
				  LocalDateTime expiredAt,
				  ShippingInfo shippingInfo,
				  User user,
				  Product product) {
		this.status = status;
		this.finalPrice = finalPrice;
		this.expiredAt = expiredAt;
		this.shippingInfo = shippingInfo;
		this.user = user;
		this.product = product;
	}

	public void addShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

}
