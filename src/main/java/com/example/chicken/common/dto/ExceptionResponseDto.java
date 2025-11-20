package com.example.chicken.common.dto;

import java.util.List;

public record ExceptionResponseDto(Integer status, String message, List<FieldErrors> errors) {

	public record FieldErrors(String field, String value, String reason) {
	}
}
