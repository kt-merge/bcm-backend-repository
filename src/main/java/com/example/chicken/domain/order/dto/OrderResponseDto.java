package com.example.chicken.domain.order.dto;

import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.entity.ShippingInfo;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import java.math.BigDecimal;

public record OrderResponseDto(Long orderId, String productName, String orderNumber, BigDecimal bidPrice,
                               OrderStatus orderStatus,
                               ShippingInfo shippingInfo, ProductResponseDto product) {

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(order.getId(),
                order.getProduct().getName(),
                order.getOrderNumber(),
                order.getFinalPrice(),
                order.getStatus(),
                order.getShippingInfo(),
                null);
    }

    public static OrderResponseDto of(Order order, ProductResponseDto product) {
        return new OrderResponseDto(order.getId(),
                order.getProduct().getName(),
                order.getOrderNumber(),
                order.getFinalPrice(),
                order.getStatus(),
                order.getShippingInfo(),
                product);
    }

}
