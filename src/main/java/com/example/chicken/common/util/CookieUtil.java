package com.example.chicken.common.util;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

	private CookieUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static ResponseCookie generateCookieResponse(String name, String value, Duration duration) {
		return ResponseCookie.from(name, value)
			.httpOnly(true)
			.secure(true)
			.sameSite("Strict")
			.path("/")
			.maxAge(duration)
			.build();
	}

	public static Cookie generateCookie(String name, String value, Long maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(Integer.parseInt(maxAge.toString()));

		return cookie;
	}
}
