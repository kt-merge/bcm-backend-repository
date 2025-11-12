package com.example.chicken.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.domain.product.Category;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductStatus;
import com.example.chicken.dto.UserResponseDto;

import lombok.Builder;

@Builder
public record ProductResponseDto(Long id, String name, String description, Category category, BigDecimal startPrice,
								 BigDecimal bidPrice, ProductStatus productStatus, String imageUrl,
								 UserResponseDto user, LocalDateTime createdAt, LocalDateTime modifiedAt) {

	public static ProductResponseDto from(Product product) {
		return ProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.category(product.getCategory())
			.startPrice(product.getStartPrice())
			.bidPrice(product.getBidPrice())
			.productStatus(product.getProductStatus())
			.imageUrl(product.getImageUrl())
			.user(UserResponseDto.from(product.getUser()))
			.createdAt(product.getCreatedAt())
			.modifiedAt(product.getModifiedAt())
			.build();
	}
}
