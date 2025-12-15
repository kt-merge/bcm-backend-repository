package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable);

    Optional<Product> findByIdWithDetails(Long productId);

    List<Product> findTop10ByUserWithDetails(User user);

}
