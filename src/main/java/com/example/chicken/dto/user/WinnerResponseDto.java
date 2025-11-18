package com.example.chicken.dto.user;

import java.math.BigDecimal;

import com.example.chicken.domain.product.HighestBidder;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductStatus;

public record WinnerResponseDto(Long productId, String productName, ProductStatus productStatus, BigDecimal bidPrice) {

	public static WinnerResponseDto from(HighestBidder highestBidder) {
		Product product = highestBidder.getProduct();

		return new WinnerResponseDto(product.getId(),
									 product.getName(),
									 product.getProductStatus(),
									 highestBidder.getLastPrice());
	}

}
