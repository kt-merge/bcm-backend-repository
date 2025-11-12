package com.example.chicken.service;

import org.springframework.stereotype.Service;

import com.example.chicken.domain.product.ProductBid;
import com.example.chicken.dto.product.ProductBidResponseDto;
import com.example.chicken.repository.ProductBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductBidService {
	private final ProductBidRepository productBidRepository;

	public ProductBidResponseDto getLastBidForProduct(Long productId) {
		ProductBid productBid = this.productBidRepository.findTopByProductIdOrderByCreatedAtDesc(productId)
			.orElseThrow(() -> new IllegalArgumentException("No bids found for product with ID: " + productId));

		return ProductBidResponseDto.from(productBid);
	}
}
