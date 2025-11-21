package com.example.chicken.common.jwt.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = JwtValidator.class)
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsJwt {
	String message() default "Invalid JWT format";
	Class<?>[] groups() default {};
	Class<? extends jakarta.validation.Payload>[] payload() default {};
}
