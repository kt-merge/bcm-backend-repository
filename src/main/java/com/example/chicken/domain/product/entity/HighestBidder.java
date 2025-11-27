package com.example.chicken.domain.product.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HighestBidder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long winnerId;

	private BigDecimal lastPrice;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_bid_id", nullable = false)
	private ProductBid productBid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Builder
	private HighestBidder(ProductBid productBid, Product product) {
		this.productBid = productBid;
		this.product = product;
		this.winnerId = productBid.getUser().getId();
		this.lastPrice = productBid.getPrice();
	}


}
