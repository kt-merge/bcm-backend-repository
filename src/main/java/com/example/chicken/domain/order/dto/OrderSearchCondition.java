package com.example.chicken.domain.order.dto;

import com.example.chicken.domain.order.entity.OrderStatus;

public record OrderSearchCondition(OrderStatus orderStatus, Long userId) {
}
