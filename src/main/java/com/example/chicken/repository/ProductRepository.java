package com.example.chicken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
