package com.example.chicken.common.jwt;

import com.example.chicken.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey;
    private int expirationTime;
    private Key key;

    private JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                               @Value("${jwt.expiration-time}") int expirationTime) {

        this.secretKey = secretKey;
        this.expirationTime = expirationTime;

        this.key =
                new SecretKeySpec(java.util.Base64.getDecoder().decode(this.secretKey), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createJWT(String email, Role role) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put("role", role.name());

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
