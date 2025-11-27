package com.example.chicken.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestResetPasswordDto(@NotBlank String email) {
}
