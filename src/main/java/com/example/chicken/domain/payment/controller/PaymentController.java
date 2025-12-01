package com.example.chicken.domain.payment.controller;

import static com.example.chicken.common.constant.PathConstant.Payment.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(PAYMENT_PREFIX)
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public ResponseEntity<Object> postPayment(@Valid PaymentRequestDto requestDto) {
		this.paymentService.createPayment(requestDto);
		return ResponseEntity.ok(null);
	}

}
