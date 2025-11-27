package com.example.chicken.domain.auth.entity.token;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

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
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class ResetPasswordToken {

	@Id
	private String id;

	@Indexed
	private String resetToken;

	@Builder
	private ResetPasswordToken(String email, String resetToken) {
		this.id = email;
		this.resetToken = resetToken;
	}

}