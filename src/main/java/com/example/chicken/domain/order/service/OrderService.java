package com.example.chicken.domain.order.service;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.service.UserMapper;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.ShippingInfoRequestDto;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.entity.ShippingInfo;
import com.example.chicken.domain.order.exception.OrderNotFoundException;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.dto.ProductImageResponseDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.service.CategoryMapper;
import com.example.chicken.domain.product.service.ProductImageMapper;
import com.example.chicken.domain.product.service.ProductMapper;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

    @Transactional
    public Order createOrder(User user, Product product) {

        LocalDateTime expiredAt = LocalDateTime.now(ZoneOffset.UTC).plusDays(1);

        Order order = Order.builder()
                .finalPrice(product.getBidPrice())
                .expiredAt(expiredAt)
                .status(OrderStatus.PAYMENT_PENDING)
                .user(user)
                .product(product)
                .build();

        return this.orderRepository.save(order);
    }

    @Transactional
    public OrderResponseDto addShippingInfo(Long orderId, ShippingInfoRequestDto requestDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = this.orderRepository.findByIdAndUser(orderId, email)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        ShippingInfo shippingInfo = this.orderMapper.toShippingEntity(requestDto);

        order.addShippingInfo(shippingInfo);

        return this.orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = this.orderRepository.findByIdAndUser(orderId, email)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        List<ProductImageResponseDto> productImageResponseDtoList = order.getProduct().getImages()
                .stream()
                .map(this.productImageMapper::toResponseDto)
                .toList();

        ProductResponseDto productResponse = productMapper.toResponseDto(order.getProduct(),
                userMapper.toResponse(order.getUser()), categoryMapper.toResponseDto(order.getProduct().getCategory()),
                productImageResponseDtoList);

        return this.orderMapper.toDto(order, productResponse);
    }

}
