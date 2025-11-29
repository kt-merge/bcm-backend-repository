package com.example.chicken.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o JOIN FETCH o.product WHERE o.user = :user")
	List<Order> findByUser(User user);

}
