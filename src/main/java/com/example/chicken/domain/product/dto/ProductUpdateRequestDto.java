package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record ProductUpdateRequestDto(@NotBlank String name,
                                      String description,
                                      Long categoryId,
                                      ProductStatus productStatus,
                                      LocalDateTime bidEndDate,
                                      @NotBlank
                                      String thumbnail,
                                      @NotNull
                                      @Size(min = 1, max = 100)
                                      List<String> imageUrls) {
}