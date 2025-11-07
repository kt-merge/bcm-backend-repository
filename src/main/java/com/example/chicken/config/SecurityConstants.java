package com.example.chicken.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    protected static final String[] AUTH_WHITELIST = {
            "/api/auth/**"
    };

}
