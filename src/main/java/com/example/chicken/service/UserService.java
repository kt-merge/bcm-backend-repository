package com.example.chicken.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.User;
import com.example.chicken.dto.UpdateUserNicknameDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.dto.user.UpdateUserInfoDto;
import com.example.chicken.repository.ProductRepository;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public UserResponseDto getUserInfo() {
		User user = getUser();
		List<ProductResponseDto> productResponse = this.productRepository.findByUser(user)
			.stream()
			.map(ProductResponseDto::from)
			.toList();

		return UserResponseDto.of(user, productResponse);
	}

	@Transactional
	public UserResponseDto updateNickname(UpdateUserNicknameDto request) {
		User user = getUser();

		user.updateNickname(request.nickname());

		User savedUser = this.userRepository.save(user);

		return UserResponseDto.from(savedUser);
	}

	@Transactional
	public UserResponseDto updateUserInfo(UpdateUserInfoDto request) {
		User user = getUser();

		user.updateUserInfo(request);

		return UserResponseDto.from(this.userRepository.save(user));
	}

	private User getUser() {
		String email =
			SecurityContextHolder.getContext().getAuthentication().getName();

		return this.userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));
	}

}
