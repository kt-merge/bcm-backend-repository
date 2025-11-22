package com.example.chicken.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

class CookieUtilTest {

	@Test
	@DisplayName("[Unit Test] CookieUtil - generateCookieResponse")
	void generateCookieResponse() {
		String name = "testName";
		String value = "testValue";
		Duration duration = Duration.ofMinutes(5);

		ResponseCookie cookie = CookieUtil.generateCookieResponse(name, value, duration);

		assertEquals(name, cookie.getName());
		assertEquals(value, cookie.getValue());
		assertEquals(duration, cookie.getMaxAge());

	}

}