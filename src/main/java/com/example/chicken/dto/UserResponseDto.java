package com.example.chicken.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.chicken.domain.Role;
import com.example.chicken.domain.User;
import com.example.chicken.dto.product.ProductBidResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.dto.user.WinnerResponseDto;

public record UserResponseDto(Long id, String nickname, String email, Role role, String phoneNumber,
							  LocalDateTime createdAt, LocalDateTime modifiedAt, List<WinnerResponseDto> winners,
							  List<ProductBidResponseDto> productBids, List<ProductResponseDto> products) {

	public static UserResponseDto from(User user) {
		return new UserResponseDto(user.getId(),
								   user.getNickname(),
								   user.getEmail(),
								   user.getRole(),
								   user.getPhoneNumber(),
								   user.getCreatedAt(),
								   user.getModifiedAt(),
								   new ArrayList<>(),
								   new ArrayList<>(),
								   new ArrayList<>());
	}

	public static UserResponseDto of(User user,
									 List<WinnerResponseDto> winners,
									 List<ProductBidResponseDto> productBids,
									 List<ProductResponseDto> products) {
		return new UserResponseDto(user.getId(),
								   user.getNickname(),
								   user.getEmail(),
								   user.getRole(),
								   user.getPhoneNumber(),
								   user.getCreatedAt(),
								   user.getModifiedAt(),
								   winners,
								   productBids,
								   products);
	}

}