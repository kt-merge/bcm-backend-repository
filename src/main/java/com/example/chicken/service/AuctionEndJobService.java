package com.example.chicken.service;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.event.OrderCreateRequestedEvent;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.HighestBidder;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.exception.ProductNotFoundException;
import com.example.chicken.dto.mail.AuctionWonEvent;
import com.example.chicken.domain.product.repository.HighestBidderRepository;
import com.example.chicken.domain.product.repository.ProductBidRepository;
import com.example.chicken.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionEndJobService {

	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final HighestBidderRepository highestBidderRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public void endProductAuction(Long productId) {

		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId.toString()));

		// 이미 완료된 경매인 경우 아무 작업도 수행하지 않음
		if (product.getBidStatus().equals(BidStatus.COMPLETED))
			return;

		Optional<ProductBid> productBid = this.productBidRepository.findTopByProductIdOrderByPriceDesc(product.getId());

		if (productBid.isPresent()) {
			User user = productBid.get().getUser();

			HighestBidder highestBidder = HighestBidder.builder()
				.productBid(productBid.get())
				.product(product)
				.build();

			this.highestBidderRepository.save(highestBidder);
			this.eventPublisher.publishEvent(new OrderCreateRequestedEvent(user.getId(), productId));
			this.eventPublisher.publishEvent(new AuctionWonEvent(product.getName(), user.getEmail()));
		}

		product.waitPayment();

		this.productRepository.save(product);
	}

}
