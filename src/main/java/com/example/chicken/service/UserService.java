package com.example.chicken.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.chicken.domain.User;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUserInfo() {
		String email =
			SecurityContextHolder.getContext().getAuthentication().getName();

		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException(""));

		return UserResponseDto.from(user);
	}

}
