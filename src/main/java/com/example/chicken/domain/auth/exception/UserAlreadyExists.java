package com.example.chicken.domain.auth.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class UserAlreadyExists extends BusinessException {
    public UserAlreadyExists() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
