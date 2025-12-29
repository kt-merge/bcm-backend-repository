package com.example.chicken.domain.payment.gateway;

import com.example.chicken.domain.payment.entity.PgProvider;
import com.example.chicken.domain.payment.exception.PaymentNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayFactory {

    private final Map<PgProvider, PaymentGateway> gatewayMap;

    public PaymentGatewayFactory(List<PaymentGateway> gateways) {
        this.gatewayMap = gateways.stream()
                .collect(Collectors.toMap(PaymentGateway::getPgProvider, Function.identity()));
    }

    public PaymentGateway getPaymentGateway(PgProvider pgProvider) {
        PaymentGateway paymentGateway = gatewayMap.get(pgProvider);

        if (paymentGateway == null) {
            throw new PaymentNotFoundException(pgProvider.toString());
        }

        return paymentGateway;
    }

}
