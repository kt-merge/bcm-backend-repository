package com.example.chicken.domain.order.entity;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal finalPrice;

    private LocalDateTime expiredAt;

    @Embedded
    private ShippingInfo shippingInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Order(OrderStatus status,
                  BigDecimal finalPrice,
                  LocalDateTime expiredAt,
                  ShippingInfo shippingInfo,
                  User user,
                  Product product) {
        this.status = status;
        this.finalPrice = finalPrice;
        this.expiredAt = expiredAt;
        this.shippingInfo = shippingInfo;
        this.user = user;
        this.product = product;
    }

    @PrePersist
    public void generateOrderNumber() {
        if (this.orderNumber == null) {
            String PREFIX = "ORD";
            DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
            int RANDOM_PART_LENGTH = 8;

            String datePart = LocalDate.now().format(DATE_FORMATTER);
            String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, RANDOM_PART_LENGTH)
                    .toUpperCase();

            this.orderNumber = String.format("%s-%s-%s", PREFIX, datePart, randomPart);
        }
    }

    public static Order pendingOrder(User user, Product product) {
        return Order.builder()
                .status(OrderStatus.PAYMENT_PENDING)
                .finalPrice(calculateFinalPrice(product.getBidPrice()))
                .expiredAt(LocalDateTime.now(ZoneOffset.UTC).plusDays(1))
                .user(user)
                .product(product)
                .build();
    }

    private static BigDecimal calculateFinalPrice(BigDecimal bidPrice) {
        BigDecimal feeRate = BigDecimal.valueOf(0.1);
        BigDecimal deliveryFee = BigDecimal.valueOf(3000);

        BigDecimal fee = bidPrice.multiply(feeRate);
        return bidPrice.add(deliveryFee).add(fee);
    }

    public void paid() {
        this.status = OrderStatus.PAID;
    }

    public Boolean isPaid() {
        return this.status == OrderStatus.PAID;
    }

    public void addShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

}
