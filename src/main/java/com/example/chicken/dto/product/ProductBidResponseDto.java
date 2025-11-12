package com.example.chicken.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.domain.User;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductBid;

public record ProductBidResponseDto(Long productId,
									BigDecimal price,
									String bidderNickname,
									Long bidCount,
									LocalDateTime bidTime) {

	public static ProductBidResponseDto from(ProductBid productBid) {

		Product product = productBid.getProduct();

		User user = productBid.getUser();

		return new ProductBidResponseDto(product.getId(),
										 productBid.getPrice(),
										 user.getNickname(),
										 product.getBidCount(),
										 productBid.getCreatedAt());
	}
}
