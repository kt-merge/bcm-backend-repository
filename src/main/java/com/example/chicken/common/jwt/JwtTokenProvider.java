package com.example.chicken.common.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtTokenProvider {

    private String secretKey;
    private int expirationTime;
    private Key key;

    @PostConstruct
    protected void init(@Value("${jwt.secret-key}") String secretKey,
                        @Value("${jwt.expiration-time}") int expirationTime){

        this.secretKey = secretKey;
        this.expirationTime = expirationTime;

        new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS512.getJcaName());
    }


}
