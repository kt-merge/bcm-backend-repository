package com.example.chicken.domain.payment.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class PaymentNotFoundException extends EntityNotFoundException {
	private static final String MESSAGE = "결제를 찾을 수 없습니다: ";
	public PaymentNotFoundException(String target) {
		super(MESSAGE + target);
	}

}
