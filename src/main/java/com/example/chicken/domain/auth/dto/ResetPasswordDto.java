package com.example.chicken.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(@NotBlank String password, @NotBlank String resetToken) {
}