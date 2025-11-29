package com.example.chicken.domain.auth.dto.user;

import java.math.BigDecimal;

import com.example.chicken.domain.product.entity.HighestBidder;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductStatus;

public record WinnerResponseDto(Long productId, String productName, ProductStatus productStatus, BigDecimal bidPrice) {

	public static WinnerResponseDto from(HighestBidder highestBidder) {
		Product product = highestBidder.getProduct();

		return new WinnerResponseDto(product.getId(),
									 product.getName(),
									 product.getProductStatus(),
									 highestBidder.getLastPrice());
	}

}
