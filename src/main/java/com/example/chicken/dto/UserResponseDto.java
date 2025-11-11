package com.example.chicken.dto;

import java.time.LocalDateTime;

import com.example.chicken.domain.Role;
import com.example.chicken.domain.User;

public record UserResponseDto(Long id, String nickname, String email, Role role, String phoneNumber,
							  LocalDateTime createdAt, LocalDateTime modifiedAt) {

	public static UserResponseDto from(User user) {
		return new UserResponseDto(user.getId(),
								   user.getNickname(),
								   user.getEmail(),
								   user.getRole(),
								   user.getPhoneNumber(),
                                   user.getCreatedAt(),
                                   user.getModifiedAt());
	}

}