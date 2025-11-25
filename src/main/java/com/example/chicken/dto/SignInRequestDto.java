package com.example.chicken.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(@NotBlank String email, @NotBlank String password) {
}