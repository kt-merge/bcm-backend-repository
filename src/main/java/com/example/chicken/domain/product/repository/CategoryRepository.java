package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByCode(String code);

    Boolean existsByName(String name);
}
