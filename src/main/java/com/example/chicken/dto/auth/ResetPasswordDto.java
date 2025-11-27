package com.example.chicken.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(@NotBlank String password, @NotBlank String resetToken) {
}
