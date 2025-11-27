package com.example.chicken.common.error.exception;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(String target) {
		super(target, ErrorCode.ENTITY_NOT_FOUND);
	}
}
