package com.example.chicken.domain.auth.dto.auth;

import com.example.chicken.domain.auth.entity.user.Role;
import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(@NotBlank String email, @NotBlank String password, Boolean rememberMe, Role userType) {
}
