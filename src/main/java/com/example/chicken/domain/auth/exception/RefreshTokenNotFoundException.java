package com.example.chicken.domain.auth.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class RefreshTokenNotFoundException extends EntityNotFoundException {

	public RefreshTokenNotFoundException(String target) {
		super("Refresh token not found: " + target);
	}

}
