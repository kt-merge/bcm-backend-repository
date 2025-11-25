package com.example.chicken.common.util;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

	private CookieUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static ResponseCookie generateCookieResponse(String name, String value, Duration duration) {
		return ResponseCookie.from(name, value)
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.path("/")
			.maxAge(duration)
			.build();
	}

}
