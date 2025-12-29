package com.example.chicken.domain.order.service;

import static com.example.chicken.common.util.SecurityUtil.getCurrentUserEmail;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.service.UserMapper;
import com.example.chicken.domain.auth.service.UserQueryService;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.dto.OrderSearchCondition;
import com.example.chicken.domain.order.dto.ShippingInfoRequestDto;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.example.chicken.domain.order.entity.ShippingInfo;
import com.example.chicken.domain.order.exception.OrderNotFoundException;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.service.CategoryMapper;
import com.example.chicken.domain.product.service.ProductImageMapper;
import com.example.chicken.domain.product.service.ProductMapper;
import com.example.chicken.domain.product.service.ProductService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;
    private final ProductService productService;
    private final UserQueryService userQueryService;

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
    public Page<OrderResponseDto> getOrders(OrderSearchCondition condition, Pageable pageable) {

        User user = this.userQueryService.getUserByEmail(getCurrentUserEmail());
        OrderSearchCondition newCondition = new OrderSearchCondition(condition.orderStatus(), user.getId());

        Page<Order> result = this.orderRepository.searchOrders(newCondition, pageable);

        return result.map((order) -> this.orderMapper.toDto(
                order, this.productMapper.toResponseDto(
                        order.getProduct(),
                        userMapper.toResponse(user),
                        categoryMapper.toResponseDto(order.getProduct().getCategory()),
                        order.getProduct()
                                .getImages()
                                .stream()
                                .map(this.productImageMapper::toResponseDto)
                                .toList()
                )));
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = this.orderRepository.findByIdAndUser(orderId, email)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return this.orderMapper.toDto(order, this.productService.convertToDto(order.getProduct()));
    }

}
