package com.example.chicken.domain.payment.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class PaymentConfirmException extends BusinessException {
    
    public PaymentConfirmException(ErrorCode errorCode) {
        super(errorCode);
    }

}
