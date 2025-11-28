package com.example.chicken.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
