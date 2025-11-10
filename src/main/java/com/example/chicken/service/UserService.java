package com.example.chicken.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.User;
import com.example.chicken.dto.UpdateUserNicknameDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserResponseDto getUserInfo() {
		return UserResponseDto.from(getUser());
	}

	@Transactional
	public UserResponseDto updateNickname(UpdateUserNicknameDto request) {
		User user = getUser();

		user.updateNickname(request.nickname());

		User savedUser = this.userRepository.save(user);

		return UserResponseDto.from(savedUser);
	}

	private User getUser() {
		String email =
			SecurityContextHolder.getContext().getAuthentication().getName();

		return this.userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));
	}

}
