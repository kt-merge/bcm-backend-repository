package com.example.chicken.common.jwt.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JwtValidator implements ConstraintValidator<IsJwt, String> {

	private static final String JWT_REGEX = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return
			value.matches(JWT_REGEX);
	}

}
