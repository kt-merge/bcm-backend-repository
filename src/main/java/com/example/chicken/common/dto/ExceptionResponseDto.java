package com.example.chicken.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.chicken.common.error.exception.ErrorCode;

public record ExceptionResponseDto(Integer status, String message, List<FieldErrors> errors) {

	public record FieldErrors(String field, String value, String reason) {
	}

	public static ExceptionResponseDto from(ErrorCode errorCode) {
		return new ExceptionResponseDto(errorCode.getStatus(), errorCode.getMessage(), new ArrayList<>());
	}
}
