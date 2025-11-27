package com.example.chicken.common.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	EXPIRED_TOKEN("AUTH001", HttpStatus.GONE.value(), "만료된 토큰입니다.");

	private final String code;
	private final int status;
	private final String message;
}
