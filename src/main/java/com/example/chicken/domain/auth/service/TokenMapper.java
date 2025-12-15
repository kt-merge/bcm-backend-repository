package com.example.chicken.domain.auth.service;

import org.springframework.stereotype.Component;

import com.example.chicken.domain.auth.dto.user.TokenResponseDto;

@Component
public class TokenMapper {

	public TokenResponseDto toDto(String accessToken, String refreshToken) {
		return new TokenResponseDto(accessToken, refreshToken);
	}

}
