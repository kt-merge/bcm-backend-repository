package com.example.chicken.common.util;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

	private CookieUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Cookie generateCookie(String name, String value, Long maxAge) {
		ResponseCookie responseCookie = ResponseCookie.from(name, value)
			.httpOnly(true)
			.secure(true)
			.sameSite("Strict")
			.path("/")
			.maxAge(maxAge)
			.build();

		return new Cookie(name, responseCookie.toString());
	}
}
