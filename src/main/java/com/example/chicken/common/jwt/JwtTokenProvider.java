package com.example.chicken.common.jwt;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {

    private String secretKey;
    private int expirationTime;

    @PostConstruct
    protected void init(@Value("${jwt.secret-key}") String secretKey,
                        @Value("${jwt.expiration-time}") int expirationTime){
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

}
