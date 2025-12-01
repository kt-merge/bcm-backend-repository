package com.example.chicken.domain.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.exception.OrderNotFoundException;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.entity.Payment;
import com.example.chicken.domain.payment.exception.PaymentNotFoundException;
import com.example.chicken.domain.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;

	@Transactional
	public void createPayment(PaymentRequestDto requestDto) {
		Long orderId = requestDto.orderId();

		Order order = this.orderRepository.findById(orderId)
			.orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

		Payment payment = this.paymentRepository.findByOrder(order)
			.orElseThrow(() -> new PaymentNotFoundException(orderId.toString()));

		order.paid();
		payment.paid();
	}

}
