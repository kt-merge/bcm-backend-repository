package com.example.chicken.domain.order.repository;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @Query("SELECT o FROM Order o JOIN FETCH o.product JOIN FETCH o.user WHERE o.user = :user and o.status NOT IN ('EXPIRED')")
    List<Order> findByUser(User user);

    @Query("SELECT o "
            + "FROM Order o "
            + "JOIN FETCH o.product JOIN FETCH o.user "
            + "WHERE o.id = :orderId AND o.user.email = :email and o.status NOT IN ('EXPIRED')")
    Optional<Order> findByIdAndUser(Long orderId, String email);

}
