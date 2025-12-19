package com.example.chicken.service;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.payment.entity.Payment;
import com.example.chicken.domain.payment.repository.PaymentRepository;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.HighestBidder;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.repository.HighestBidderRepository;
import com.example.chicken.domain.product.repository.ProductBidRepository;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.dto.mail.AuctionWonEvent;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionEndJobService {

    private final ProductRepository productRepository;
    private final ProductBidRepository productBidRepository;
    private final HighestBidderRepository highestBidderRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void endProductAuction(Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseGet(() -> {
                    log.warn("프로덕트 ID {}번 존재하지 않음", productId);
                    return null;
                });

        if (product == null || product.getBidStatus() == BidStatus.COMPLETED) {
            return;
        }

        Optional<ProductBid> productBid = this.productBidRepository.findTopByProductIdOrderByPriceDesc(product.getId());

        if (productBid.isPresent()) {
            User user = productBid.get().getUser();

            this.highestBidderRepository.save(HighestBidder.of(productBid.get(), product));
            Order savedOrder = this.orderRepository.save(Order.pendingOrder(user, product));
            this.paymentRepository.save(Payment.ready(user, savedOrder));

            this.eventPublisher.publishEvent(new AuctionWonEvent(product.getName(), user.getEmail()));
        }

        if (product.getBidStatus().equals(BidStatus.NOT_BIDDED)) {
            product.completeBid();
        } else {
            product.waitPayment();
        }

        this.productRepository.save(product);
    }

}
