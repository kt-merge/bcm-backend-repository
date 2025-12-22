package com.example.chicken.domain.payment.service;

import com.example.chicken.common.error.exception.ErrorCode;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.exception.OrderNotFoundException;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.entity.Payment;
import com.example.chicken.domain.payment.entity.PgProvider;
import com.example.chicken.domain.payment.exception.PaymentConfirmException;
import com.example.chicken.domain.payment.exception.PaymentNotFoundException;
import com.example.chicken.domain.payment.gateway.PaymentGateway;
import com.example.chicken.domain.payment.gateway.PaymentGatewayFactory;
import com.example.chicken.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayFactory paymentGatewayFactory;

    @Transactional
    public void confirmPayment(PaymentRequestDto requestDto, PgProvider pgProvider) {
        Long orderId = requestDto.orderId();

        Order order = this.orderRepository.findById(requestDto.orderId())
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        if (order.getFinalPrice().compareTo(requestDto.amount()) != 0) {
            throw new PaymentConfirmException(ErrorCode.PAYMENT_AMOUNT_MISMATCH_EXCEPTION);
        }

        if (order.isPaid()) {
            throw new PaymentConfirmException(ErrorCode.PAYMENT_ALREADY_PAID_EXCEIPTION);
        }

        Payment payment = this.paymentRepository.findByOrder(order)
                .orElseThrow(() -> new PaymentNotFoundException(orderId.toString()));

        PaymentGateway paymentGateway = this.paymentGatewayFactory.getPaymentGateway(pgProvider);

        paymentGateway.confirmPayment(requestDto);

        order.paid();
        payment.paid();
    }

}
