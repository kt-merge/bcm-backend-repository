package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_ORDERS_PREFIX;

import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.OrderSearchCondition;
import com.example.chicken.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_ORDERS_PREFIX)
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getOrders(OrderSearchCondition condition, Pageable pageable) {

        Page<OrderResponseDto> result = this.orderService.getOrders(condition, pageable);

        return ResponseEntity.ok(result);
    }

}
