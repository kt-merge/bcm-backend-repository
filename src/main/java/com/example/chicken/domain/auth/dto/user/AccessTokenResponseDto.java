package com.example.chicken.domain.auth.dto.user;

public record AccessTokenResponseDto(String accessToken) {

	public static AccessTokenResponseDto from(String accessToken) {
		return new AccessTokenResponseDto(accessToken);
	}

}