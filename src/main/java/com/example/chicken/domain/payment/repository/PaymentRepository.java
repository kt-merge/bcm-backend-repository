package com.example.chicken.domain.payment.repository;

import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);
}
