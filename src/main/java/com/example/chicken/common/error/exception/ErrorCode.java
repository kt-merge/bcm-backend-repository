package com.example.chicken.common.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	USER_ALREADY_EXISTS("AUTH001", HttpStatus.CONFLICT.value(), "이미 존재하는 사용자입니다."),
	PASSWORD_NOT_MATCHED("AUTH002", HttpStatus.UNAUTHORIZED.value(), "비밀번호가 일치하지 않습니다."),

	ENTITY_NOT_FOUND("COMM001", HttpStatus.NOT_FOUND.value(), "엔티티를 찾을 수 없습니다."),

	EXPIRED_TOKEN("AUTH001", HttpStatus.GONE.value(), "만료된 토큰입니다.");

	private final String code;
	private final int status;
	private final String message;
}
