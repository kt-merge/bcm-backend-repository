package com.example.chicken.dto;

import com.example.chicken.domain.User;

public record UserResponseDto(Long id, String nickname, String email) {

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getEmail());
    }
}