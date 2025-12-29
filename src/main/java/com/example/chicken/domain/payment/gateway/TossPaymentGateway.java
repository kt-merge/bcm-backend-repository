package com.example.chicken.domain.payment.gateway;

import com.example.chicken.common.error.exception.ErrorCode;
import com.example.chicken.domain.payment.dto.PaymentRequestDto;
import com.example.chicken.domain.payment.entity.PgProvider;
import com.example.chicken.domain.payment.exception.PaymentConfirmException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TossPaymentGateway implements PaymentGateway {

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public PgProvider getPgProvider() {
        return PgProvider.TOSS;
    }

    @Override
    public void confirmPayment(PaymentRequestDto request) {

        String secretKeyWithColon = this.secretKey + ":";

        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKeyWithColon.getBytes());

        String URL = "https://api.tosspayments.com/v1/payments/confirm";

        try {

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Authorization", "Basic " + encodedSecretKey)
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(
                            this.objectMapper.writeValueAsString(request)))
                    .build();

            HttpResponse<String> response = this.httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new PaymentConfirmException(ErrorCode.PAYMENT_CONFIRM_FAILED);
            }

        } catch (JsonProcessingException exception) {

            throw new PaymentConfirmException(ErrorCode.PAYMENT_REQUEST_SERIALIZATION_FAILED);

        } catch (IOException | InterruptedException exception) {

            throw new PaymentConfirmException(ErrorCode.PAYMENT_API_CONNECTION_FAILED);

        }
    }
}