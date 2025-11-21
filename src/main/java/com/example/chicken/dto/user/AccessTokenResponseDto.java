package com.example.chicken.dto.user;

public record AccessTokenResponseDto(String accessToken) {

	public static AccessTokenResponseDto from(String accessToken) {
		return new AccessTokenResponseDto(accessToken);
	}

}