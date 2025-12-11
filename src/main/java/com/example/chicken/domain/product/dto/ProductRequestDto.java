package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequestDto(@NotBlank String name,
                                String description,
                                Long categoryId,
                                BigDecimal price,
                                ProductStatus productStatus,
                                LocalDateTime bidEndDate,
                                @NotBlank String imageUrl) {
}
