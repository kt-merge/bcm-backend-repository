package com.example.chicken.domain.order.repository;

import com.example.chicken.domain.order.dto.OrderSearchCondition;
import com.example.chicken.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<Order> searchOrders(OrderSearchCondition condition, Pageable pageable);

}
