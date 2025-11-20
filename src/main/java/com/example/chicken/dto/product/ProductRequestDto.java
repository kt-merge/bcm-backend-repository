package com.example.chicken.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.domain.product.Category;
import com.example.chicken.domain.product.ProductStatus;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDto(@NotBlank String name,
								String description,
								@NotBlank Category category,
								@NotBlank BigDecimal price,
								@NotBlank ProductStatus productStatus,
								LocalDateTime bidEndDate,
								@NotBlank String imageUrl) {
}
