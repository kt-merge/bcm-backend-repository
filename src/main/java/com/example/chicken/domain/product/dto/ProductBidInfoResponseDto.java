package com.example.chicken.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.ProductBid;

public record ProductBidInfoResponseDto(Long productBidId,
										BigDecimal price,
										String bidderNickname,
										LocalDateTime bidTime) {

	public static ProductBidInfoResponseDto from(ProductBid productBid) {
		User user = productBid.getUser();

		return new ProductBidInfoResponseDto(productBid.getId(),
											 productBid.getPrice(),
											 user.getNickname(),
											 productBid.getCreatedAt());
	}
}
