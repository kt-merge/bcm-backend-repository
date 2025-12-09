package com.example.chicken.common.config;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    protected static final String[] AUTH_WHITELIST = {
            "/api/auth/**",
            "/api/auth/reissue",
            "/connect/**",
            "/springwolf/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"
    };

    protected static final String[] ADMIN_WHITELIST = {
            "/api/admin/**",
            "/api/admin/products/**"
    };

    protected static final String[] USER_WHITELIST = {
            "/api/users/**", "/api/products/**", "/api/orders/**", "/api/s3/**"
    };

    protected static final List<String> ALLOWED_METHODS = List.of(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
    );

}
