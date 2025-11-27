package com.example.chicken.domain.product.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

	public ProductNotFoundException(String target) {
		super("상품을 찾을 수 없음: " + target);
	}

}
