package com.example.chicken.dto;

import com.example.chicken.domain.auth.dto.user.WinnerResponseDto;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.UserStatus;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.product.dto.ProductBidResponseDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(Long id, String nickname, String email, Role role, UserStatus status, String phoneNumber,
                              LocalDateTime createdAt, LocalDateTime modifiedAt, List<WinnerResponseDto> winners,
                              List<ProductBidResponseDto> productBids, List<ProductResponseDto> products,
                              List<OrderResponseDto> orders) {
}