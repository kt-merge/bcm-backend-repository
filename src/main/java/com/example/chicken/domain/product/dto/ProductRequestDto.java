package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductRequestDto(@NotBlank String name,
                                String description,
                                @NotNull Long categoryId,
                                BigDecimal price,
                                ProductStatus productStatus,
                                LocalDateTime bidEndDate,
                                @NotNull
                                @Size(min = 1, max = 100)
                                List<String> imageUrls) {
}
