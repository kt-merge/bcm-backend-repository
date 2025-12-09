package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record ProductUpdateRequestDto(@NotBlank String name,
                                      String description,
                                      Category category,
                                      ProductStatus productStatus,
                                      LocalDateTime bidEndDate,
                                      @NotBlank String imageUrl) {
}