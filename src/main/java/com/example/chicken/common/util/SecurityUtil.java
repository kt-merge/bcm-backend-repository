package com.example.chicken.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 인증 정보가 없습니다.");
        }

        return authentication.getName();
    }

}
