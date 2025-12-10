package com.example.chicken.domain.product.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank String code,
                                 @NotBlank String name) {
}
