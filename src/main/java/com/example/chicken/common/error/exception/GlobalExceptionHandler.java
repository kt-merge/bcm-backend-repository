package com.example.chicken.common.error.exception;

import static com.example.chicken.common.dto.ExceptionResponseDto.FieldErrors;
import static com.example.chicken.common.dto.ExceptionResponseDto.from;

import com.example.chicken.common.dto.ExceptionResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SecurityException.class, MalformedJwtException.class, ExpiredJwtException.class,
            UnsupportedJwtException.class, SignatureException.class})
    public ResponseEntity<ExceptionResponseDto> handleJwtException() {

        ExceptionResponseDto response = new ExceptionResponseDto(401, "Invalid JWT token", null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldErrors> fieldErrors = getFieldErrors(exception.getBindingResult());

        ExceptionResponseDto response = new ExceptionResponseDto(400, "Field validation fail", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseDto> handleBusinessException(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus())).body(from(errorCode));
    }

    private List<FieldErrors> getFieldErrors(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();

        return errors.parallelStream()
                .map(error -> new FieldErrors(
                        error.getDefaultMessage(),
                        error.getField(),
                        (String) error.getRejectedValue()))
                .toList();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(ConstraintViolationException exception) {
        List<FieldErrors> errors = getViolationErrors(exception.getConstraintViolations());

        ExceptionResponseDto response = new ExceptionResponseDto(400, "Constraint violation", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponseDto> handleException(Exception e) {

        ExceptionResponseDto response = new ExceptionResponseDto(500, "Internal Server Error", null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private static List<FieldErrors> getViolationErrors(Set<ConstraintViolation<?>> violations) {
        return violations
                .parallelStream().map(
                        error -> new FieldErrors(
                                error.getPropertyPath().toString(),
                                error.getInvalidValue().toString(),
                                error.getMessage()
                        )
                ).toList();
    }

}
