package com.example.chicken.domain.auth.dto.auth;

public record SignInResponseDto(String accessToken, String refreshToken) {
}