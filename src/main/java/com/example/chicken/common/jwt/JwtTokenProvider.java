package com.example.chicken.common.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.chicken.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private String secretKey;
	private int expirationTime;
	private Key key;

	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
							@Value("${jwt.expiration-time}") int expirationTime) {

		this.secretKey = secretKey;
		this.expirationTime = expirationTime;

		this.key =
			new SecretKeySpec(java.util.Base64.getDecoder().decode(this.secretKey),
							  SignatureAlgorithm.HS512.getJcaName());
	}

	public String createJWT(String email, Role role) {
		Claims claims = Jwts.claims().setSubject(email);

		String userRole = "ROLE_" + role.name();
		claims.put("role", userRole);

		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime * 60L * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

}
