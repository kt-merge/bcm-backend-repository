package com.example.chicken.domain.product.dto;

import lombok.Builder;

@Builder
public record ProductImageResponseDto(Long id, String imageUrl) {
}
