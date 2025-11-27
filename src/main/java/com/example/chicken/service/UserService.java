package com.example.chicken.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.common.error.exception.auth.ResetTokenExpiredException;
import com.example.chicken.common.error.exception.user.UserNotFoundException;
import com.example.chicken.domain.User;
import com.example.chicken.domain.auth.ResetPasswordToken;
import com.example.chicken.domain.product.ProductBid;
import com.example.chicken.dto.UpdateUserNicknameDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.product.ProductBidResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.dto.user.UpdateUserInfoDto;
import com.example.chicken.dto.user.WinnerResponseDto;
import com.example.chicken.repository.HighestBidderRepository;
import com.example.chicken.repository.ProductBidRepository;
import com.example.chicken.repository.ProductRepository;
import com.example.chicken.repository.ResetPasswordTokenRepository;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final HighestBidderRepository highestBidderRepository;
	private final ResetPasswordTokenRepository resetPasswordTokenRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public UserResponseDto getUserInfo() {
		User user = getUser();

		List<WinnerResponseDto> winnerResponse = this.highestBidderRepository.findByWinnerId(user.getId())
			.stream().map(WinnerResponseDto::from)
			.toList();

		List<ProductBidResponseDto> productBids = this.productBidRepository.findDistinctByUserId(user.getId())
			.stream()
			.filter(ProductBid::isHighestBidderNotExists)
			.map(ProductBidResponseDto::from)
			.toList();

		List<ProductResponseDto> productResponse = this.productRepository.findByUser(user)
			.stream()
			.map(ProductResponseDto::from)
			.toList();

		return UserResponseDto.of(user, winnerResponse, productBids, productResponse);
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
			.orElseThrow(() -> new UserNotFoundException(email));
	}

	@Transactional
	public void updatePassword(String password, String token) {
		ResetPasswordToken resetPasswordToken = this.resetPasswordTokenRepository
			.findByResetToken(token).orElseThrow(ResetTokenExpiredException::new);

		String email = resetPasswordToken.getId();

		User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		user.updatePassword(this.passwordEncoder.encode(password));

		this.resetPasswordTokenRepository.delete(resetPasswordToken);
	}

}
