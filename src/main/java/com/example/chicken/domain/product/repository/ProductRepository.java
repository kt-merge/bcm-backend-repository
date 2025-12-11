package com.example.chicken.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	List<Product> findTop10ByUserOrderByCreatedAtDesc(User user);
	List<Product> findByUser(User user);

}
