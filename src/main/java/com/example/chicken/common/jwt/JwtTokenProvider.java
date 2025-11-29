package com.example.chicken.common.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.dto.user.TokenResponseDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	private String secretKey;
	private int expirationTime;
	private Long refreshExpirationTime;

	private Key key;

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
							@Value("${jwt.expiration-time}") int expirationTime,
							@Value("${jwt.refresh-expiration-time}") long refreshExpirationTime) {
		this.secretKey = secretKey;
		this.expirationTime = expirationTime;
		this.refreshExpirationTime = refreshExpirationTime;
	}

	@PostConstruct
	public void init() {
		this.key =
			new SecretKeySpec(java.util.Base64.getDecoder().decode(this.secretKey),
							  SignatureAlgorithm.HS512.getJcaName());
	}

	public TokenResponseDto createTokens(String email, String nickname) {
		return TokenResponseDto.of(
			createJWT(email, nickname, Role.USER),
			createRefreshJWT(email));
	}

	public String createRefreshJWT(String email) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("type", "refresh_token");

		Date now = new Date();
		Date expiration = new Date(now.getTime() + refreshExpirationTime * 60L * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expiration)
			.signWith(this.key)
			.compact();
	}

	public String createJWT(String email, String nickname, Role role) {
		Claims claims = Jwts.claims().setSubject(email);

		String userRole = "ROLE_" + role.name();
		claims.put("role", userRole);
		claims.put("nickname", nickname);
		claims.put("type", "access_token");

		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime * 60L * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(this.key)
			.compact();
	}

}
