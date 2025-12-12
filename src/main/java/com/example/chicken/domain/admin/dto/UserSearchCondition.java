package com.example.chicken.domain.admin.dto;

import com.example.chicken.domain.auth.entity.user.UserStatus;

public record UserSearchCondition(Long userId, String nickname, String email, String phoneNumber,
                                  UserStatus userStatus) {
}
