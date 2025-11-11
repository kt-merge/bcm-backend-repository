package com.example.chicken.dto.product;

import java.math.BigDecimal;

import com.example.chicken.domain.product.Category;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductStatus;
import com.example.chicken.dto.UserResponseDto;

import lombok.Builder;

@Builder
public record ProductResponseDto(Long id, String name, String description, Category category, BigDecimal price,
								 ProductStatus productStatus, String imageUrl, UserResponseDto user) {

	public static ProductResponseDto from(Product product) {
		return ProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.category(product.getCategory())
			.price(product.getPrice())
			.productStatus(product.getProductStatus())
			.imageUrl(product.getImageUrl())
			.user(UserResponseDto.from(product.getUser()))
			.build();
	}
}
