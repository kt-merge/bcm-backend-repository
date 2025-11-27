package com.example.chicken.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequestDto(@NotBlank String email) {
}
