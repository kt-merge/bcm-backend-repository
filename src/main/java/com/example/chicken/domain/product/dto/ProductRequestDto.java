package com.example.chicken.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.entity.ProductStatus;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDto(@NotBlank String name,
								String description,
								Category category,
								BigDecimal price,
								ProductStatus productStatus,
								LocalDateTime bidEndDate,
								@NotBlank String imageUrl) {
}
