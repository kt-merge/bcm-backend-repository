package com.example.chicken.domain.order.controller;

import static com.example.chicken.common.constant.PathConstant.Order.ORDER_ID;
import static com.example.chicken.common.constant.PathConstant.Order.ORDER_PREFIX;
import static com.example.chicken.common.constant.PathConstant.Order.SHIPPING_INFO;

import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.ShippingInfoRequestDto;
import com.example.chicken.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_PREFIX)
public class OrderController {

    private final OrderService orderService;

    @PatchMapping(SHIPPING_INFO)
    public ResponseEntity<OrderResponseDto> addShippingInfo(@PathVariable Long orderId,
                                                            @RequestBody ShippingInfoRequestDto requestDto) {

        OrderResponseDto result = this.orderService.addShippingInfo(orderId, requestDto);

        return ResponseEntity.ok(result);
    }

    @GetMapping(ORDER_ID)
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        OrderResponseDto result = this.orderService.getOrder(orderId);

        return ResponseEntity.ok(result);
    }

}
