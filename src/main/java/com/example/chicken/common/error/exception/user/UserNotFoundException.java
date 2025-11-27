package com.example.chicken.common.error.exception.user;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

	public UserNotFoundException(String target) {
		super("유저를 찾을 수 없습니다: " + target);
	}

}
