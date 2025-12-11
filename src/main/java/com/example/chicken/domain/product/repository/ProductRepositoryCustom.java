package com.example.chicken.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.entity.Product;

public interface ProductRepositoryCustom {

	Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable);

}
