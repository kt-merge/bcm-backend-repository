package com.example.chicken.domain.payment.controller;

import static com.example.chicken.common.constant.PathConstant.Payment.PAYMENT_PREFIX;

import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.entity.PgProvider;
import com.example.chicken.domain.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PAYMENT_PREFIX)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{pgProvider}")
    public ResponseEntity<Void> postPayment(
            @PathVariable PgProvider pgProvider,
            @RequestBody @Valid PaymentRequestDto requestDto) {
        this.paymentService.confirmPayment(requestDto, pgProvider);
        return ResponseEntity.ok().build();

    }
}
