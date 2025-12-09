package com.example.chicken.domain.admin.dto;

import com.example.chicken.domain.auth.entity.user.Status;

public record UpdateUserInfoByAdminDto(String nickname, String phoneNumber, Status status) {
}
