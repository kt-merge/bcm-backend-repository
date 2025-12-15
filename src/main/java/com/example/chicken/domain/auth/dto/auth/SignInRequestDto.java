package com.example.chicken.domain.auth.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(@NotBlank String email, @NotBlank String password, Boolean rememberMe) {
}