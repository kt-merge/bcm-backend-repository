package com.example.chicken.domain.product.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequestDto(@NotBlank String code,
                                       @NotBlank String name) {
}
