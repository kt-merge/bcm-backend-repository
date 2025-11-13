package com.example.chicken.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.User;
import com.example.chicken.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findTop10ByUserOrderByCreatedAtDesc(User user);

}
