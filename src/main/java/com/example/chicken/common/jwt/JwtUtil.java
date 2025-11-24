package com.example.chicken.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class JwtUtil {

	private final Key key;

	public JwtUtil(@Value("${jwt.secret-key}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validate(String token) {
		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

		return true;
	}

	public Authentication getAuthentication(String accessToken) {

		Claims claims = parseClaims(accessToken);

		if (claims.get("role") == null)
			throw new IllegalArgumentException();

		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get("role").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		UserDetails principal =
			new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(this.key).build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
