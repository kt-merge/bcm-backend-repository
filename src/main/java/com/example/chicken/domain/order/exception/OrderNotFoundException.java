package com.example.chicken.domain.order.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
	private static final String MESSAGE = "주문을 찾을 수 없습니다: ";
	public OrderNotFoundException(String target) {
		super(MESSAGE + target);
	}

}
