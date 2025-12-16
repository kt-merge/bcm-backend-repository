package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.product.entity.ProductBid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductBidRepository extends JpaRepository<ProductBid, Long>, ProductBidRepositoryCustom {

    @EntityGraph(attributePaths = {"user", "product"})
    Optional<ProductBid> findTopByProductIdOrderByCreatedAtDesc(Long productId);

    @EntityGraph(attributePaths = {"user"})
    Optional<ProductBid> findTopByProductIdOrderByPriceDesc(Long productId);

    @EntityGraph(attributePaths = {"user", "product"})
    List<ProductBid> findTop5ByProductIdOrderByCreatedAtDesc(Long productId);

    @Query("""
            SELECT pb FROM ProductBid pb
            WHERE pb.id IN (
            SELECT MAX(pbb.id)
            FROM ProductBid pbb
            WHERE pbb.user.id = :userId
            GROUP BY pbb.product.id
            )
            ORDER BY pb.createdAt DESC
            """)
    List<ProductBid> findDistinctByUserId(Long userId);

}
