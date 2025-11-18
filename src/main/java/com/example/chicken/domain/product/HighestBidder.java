package com.example.chicken.domain.product;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class HighestBidder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long winnerId;

	private BigDecimal lastPrice;

	@OneToOne
	@JoinColumn(name = "product_bid_id", nullable = false)
	private ProductBid productBid;

	@OneToOne
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
