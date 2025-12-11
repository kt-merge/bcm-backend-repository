package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.admin.dto.DailyProductRegistrationCountDto;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findTop10ByUserOrderByCreatedAtDesc(User user);

    List<Product> findByUser(User user);

    @Query("SELECT new com.example.chicken.domain.admin.dto.DailyProductRegistrationCountDto(CAST(product.createdAt As localdate), COUNT(*)) "
            + "FROM Product product "
            + "WHERE product.createdAt >= :startDate AND product.createdAt < :endDate "
            + "GROUP BY CAST(product.createdAt As localdate) "
            + "ORDER BY CAST(product.createdAt As localdate) DESC")
    List<DailyProductRegistrationCountDto> countProductsByDay(@Param("startDate") LocalDateTime startDate,
                                                              @Param("endDate") LocalDateTime endDate);
}
