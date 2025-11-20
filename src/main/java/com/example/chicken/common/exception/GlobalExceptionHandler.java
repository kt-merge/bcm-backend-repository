package com.example.chicken.common.exception;

import static com.example.chicken.common.dto.ExceptionResponseDto.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.chicken.common.dto.ExceptionResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
		List<FieldErrors> fieldErrors = getFieldErrors(exception.getBindingResult());

		ExceptionResponseDto response = new ExceptionResponseDto(400, "Field validation fail", fieldErrors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	private List<FieldErrors> getFieldErrors(BindingResult bindingResult) {
		List<FieldError> errors = bindingResult.getFieldErrors();

		return errors.parallelStream()
			.map(error -> new FieldErrors(
				error.getDefaultMessage(),
				error.getField(),
				(String)error.getRejectedValue()))
			.toList();
	}
}
