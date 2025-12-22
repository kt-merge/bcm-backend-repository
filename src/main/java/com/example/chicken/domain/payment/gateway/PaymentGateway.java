package com.example.chicken.domain.payment.gateway;

import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.entity.PgProvider;

public interface PaymentGateway {

    PgProvider getPgProvider();

    void confirmPayment(PaymentRequestDto request);
    
}
