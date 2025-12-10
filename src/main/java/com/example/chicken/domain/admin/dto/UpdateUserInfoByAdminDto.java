package com.example.chicken.domain.admin.dto;

import com.example.chicken.domain.auth.entity.user.UserStatus;

public record UpdateUserInfoByAdminDto(String nickname, String phoneNumber, UserStatus status) {
}
