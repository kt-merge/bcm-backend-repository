package com.example.chicken.domain.auth.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class PasswordNotMatchedException extends BusinessException {
	public PasswordNotMatchedException() {
		super(ErrorCode.PASSWORD_NOT_MATCHED);
	}
}
