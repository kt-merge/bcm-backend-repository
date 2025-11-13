package com.example.chicken.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.chicken.domain.product.Category;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductStatus;
import com.example.chicken.dto.UserResponseDto;

import lombok.Builder;

@Builder
public record ProductResponseDto(Long id, String name, String description, Category category, BigDecimal startPrice,
								 BigDecimal bidPrice, Long bidCount, String bidStatus, ProductStatus productStatus,
								 String imageUrl, UserResponseDto user, LocalDateTime createdAt,
								 LocalDateTime modifiedAt, List<ProductBidInfoResponseDto> productBids) {

	public static ProductResponseDto from(Product product) {
		return ProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.category(product.getCategory())
			.startPrice(product.getStartPrice())
			.bidPrice(product.getBidPrice())
			.bidCount(product.getBidCount())
			.bidStatus(product.getBidStatus().getDescription())
			.productStatus(product.getProductStatus())
			.imageUrl(product.getImageUrl())
			.user(UserResponseDto.from(product.getUser()))
			.createdAt(product.getCreatedAt())
			.modifiedAt(product.getModifiedAt())
			.productBids(new ArrayList<>())
			.build();
	}

	public static ProductResponseDto of(Product product, List<ProductBidInfoResponseDto> productBids) {
		return ProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.category(product.getCategory())
			.startPrice(product.getStartPrice())
			.bidPrice(product.getBidPrice())
			.bidCount(product.getBidCount())
			.bidStatus(product.getBidStatus().getDescription())
			.productStatus(product.getProductStatus())
			.imageUrl(product.getImageUrl())
			.user(UserResponseDto.from(product.getUser()))
			.createdAt(product.getCreatedAt())
			.modifiedAt(product.getModifiedAt())
			.productBids(productBids)
			.build();
	}
}
