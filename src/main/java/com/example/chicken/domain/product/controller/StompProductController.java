package com.example.chicken.domain.product.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.chicken.domain.product.dto.ProductBidRequestDto;
import com.example.chicken.domain.product.dto.ProductBidResponseDto;
import com.example.chicken.service.ProductBidService;
import com.example.chicken.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompProductController {

	private final ProductService productService;
	private final ProductBidService productBidService;
	private final SimpMessageSendingOperations messageTemplate;

	/**
	 * 특정 상품(productId)의 입찰 정보 업데이트를 처리한다.
	 *
	 * @param productId 상품 ID
	 * @param request 입찰 정보요청 DTO
	 */
	@MessageMapping("/products/{productId}/product-bids")
	public void handleProductBid(@DestinationVariable Long productId, @Payload ProductBidRequestDto request) {

		boolean isSuccess = this.productService.updateProductBid(productId, request);

		ProductBidResponseDto result = this.productBidService.getLastBidForProduct(productId);

		String destination = new StringBuilder()
			.append("/topic")
			.append("/products")
			.append("/").append(productId)
			.append("/product-bids")
			.toString();

		if(isSuccess) this.messageTemplate.convertAndSend(destination, result);
		
	}

}
