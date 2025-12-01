package com.example.chicken.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.dto.ProductBidInfoResponseDto;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.dto.ProductRequestDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;

@Component
public class ProductMapper {

	public Product toEntity(ProductRequestDto request, String imageUrl, User user) {
		return Product.builder()
			.name(request.name())
			.description(request.description())
			.category(request.category())
			.startPrice(request.price())
			.bidPrice(request.price())
			.bidCount(0L)
			.bidStatus(BidStatus.NOT_BIDDED)
			.productStatus(request.productStatus())
			.bidEndDate(request.bidEndDate())
			.imageUrl(imageUrl)
			.user(user)
			.build();
	}

	public ProductResponseDto toResponseDto(Product product) {
		return ProductResponseDto.from(product);
	}

	public ProductResponseDto toResponseDto(Product product, List<ProductBidInfoResponseDto> productBids) {
		return ProductResponseDto.of(product, productBids);
	}

}
