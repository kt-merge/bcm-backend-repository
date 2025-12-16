package com.example.chicken.domain.auth.service;

import com.example.chicken.domain.auth.dto.user.WinnerResponseDto;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.entity.user.UserStatus;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.product.dto.ProductBidResponseDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDto request) {
        return User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .status(UserStatus.PENDING)
                .phoneNumber(request.phoneNumber())
                .build();
    }

    public UserResponseDto toResponse(User user) {
        return new UserResponseDto(user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public UserResponseDto toResponse(User user,
                                      List<WinnerResponseDto> winners,
                                      List<ProductBidResponseDto> productBids,
                                      List<ProductResponseDto> products,
                                      List<OrderResponseDto> orders) {
        return new UserResponseDto(user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                winners,
                productBids,
                products,
                orders);
    }
}
