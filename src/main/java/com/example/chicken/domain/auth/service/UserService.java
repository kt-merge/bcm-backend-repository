package com.example.chicken.domain.auth.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.entity.token.ResetPasswordToken;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.ResetTokenExpiredException;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.ResetPasswordTokenRepository;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.order.service.OrderMapper;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.product.ProductBidResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.dto.user.UpdateUserInfoDto;
import com.example.chicken.dto.user.WinnerResponseDto;
import com.example.chicken.repository.HighestBidderRepository;
import com.example.chicken.repository.ProductBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final HighestBidderRepository highestBidderRepository;
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
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

		List<OrderResponseDto> orderResponse = this.orderRepository.findByUser(user)
			.stream().map(orderMapper::toDto)
			.toList();

		return UserResponseDto.of(user, winnerResponse, productBids, productResponse, orderResponse);
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
