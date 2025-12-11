package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.ProductStatus;
import com.example.chicken.dto.UserResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductResponseDto(Long id, String name, String description, CategoryResponseDto category,
                                 BigDecimal startPrice,
                                 BigDecimal bidPrice, Long bidCount, BidStatus bidStatus, ProductStatus productStatus,
                                 String imageUrl, UserResponseDto user, LocalDateTime createdAt,
                                 LocalDateTime modifiedAt, LocalDateTime bidEndDate,
                                 List<ProductBidInfoResponseDto> productBids) {
}
