package com.example.chicken.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.product.BidStatus;
import com.example.chicken.domain.product.HighestBidder;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductBid;
import com.example.chicken.repository.HighestBidderRepository;
import com.example.chicken.repository.ProductBidRepository;
import com.example.chicken.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionEndJobService {

	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final HighestBidderRepository highestBidderRepository;

	@Transactional
	public void endProductAuction(Long productId) {

		Product product = this.productRepository.findById(productId).orElseThrow(() ->
																					 new IllegalArgumentException(
																						 "Product not found"));

		// 이미 완료된 경매인 경우 아무 작업도 수행하지 않음
		if (product.getBidStatus().equals(BidStatus.COMPLETED)) return;

		Optional<ProductBid> productBid = this.productBidRepository.findTopByProductIdOrderByPriceDesc(product.getId());

		if (productBid.isPresent()) {
			HighestBidder highestBidder = HighestBidder.builder()
				.productBid(productBid.get())
				.product(product)
				.build();

			this.highestBidderRepository.save(highestBidder);
		}

		product.completeBid();

		this.productRepository.save(product);
	}
}
