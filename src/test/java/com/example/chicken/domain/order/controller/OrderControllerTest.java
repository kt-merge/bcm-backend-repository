package com.example.chicken.domain.order.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.chicken.IntegrationTest;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.product.repository.ProductRepository;

class OrderControllerTest extends IntegrationTest {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ProductRepository productRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@BeforeEach
	public void setUp() {
	}

}