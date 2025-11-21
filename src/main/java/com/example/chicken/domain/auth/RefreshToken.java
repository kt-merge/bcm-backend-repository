package com.example.chicken.domain.auth;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
// 1296000 seconds = 15 days
@RedisHash(value = "refreshToken", timeToLive = 1296000)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

	@Id
	private String id;

	private String refreshJwt;

	@Builder
	private RefreshToken(String email, String refreshToken) {
		this.id = email;
		this.refreshJwt = refreshToken;
	}

	public void setRefreshToken(String token) {
		this.refreshJwt = token;
	}

}