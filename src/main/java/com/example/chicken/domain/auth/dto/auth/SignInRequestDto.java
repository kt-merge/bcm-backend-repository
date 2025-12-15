package com.example.chicken.domain.auth.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequestDto(@NotBlank String email, @NotBlank String password, @NotNull Boolean rememberMe) {
}