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
@RedisHash(value = "resetToken", timeToLive = 900)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordToken {

	@Id
	private String id;

	private String resetToken;

	@Builder
	private ResetPasswordToken(String email, String resetToken) {
		this.id = email;
		this.resetToken = resetToken;
	}

}