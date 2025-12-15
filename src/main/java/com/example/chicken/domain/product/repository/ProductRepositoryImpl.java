package com.example.chicken.domain.product.repository;

import static com.example.chicken.domain.auth.entity.user.QUser.user;
import static com.example.chicken.domain.product.entity.QCategory.category;
import static com.example.chicken.domain.product.entity.QProduct.product;
import static com.example.chicken.domain.product.entity.QProductImage.productImage;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.entity.Product;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {

        String name = condition.name();

        List<Product> result = queryFactory
                .select(product).distinct()
                .from(product)
                .leftJoin(product.user, user).fetchJoin()
                .leftJoin(product.category, category).fetchJoin()
                .leftJoin(product.images, productImage).fetchJoin()
                .where(
                        hasNameContaining(name)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        hasNameContaining(name)
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private static Predicate hasNameContaining(String name) {
        return name != null ? product.name.contains(name) : null;
    }

    @Override
    public Optional<Product> findByIdWithDetails(Long productId) {
        Product result = queryFactory
                .select(product).distinct()
                .from(product)
                .leftJoin(product.user, user).fetchJoin()
                .leftJoin(product.category, category).fetchJoin()
                .leftJoin(product.images, productImage).fetchJoin()
                .where(
                        product.id.eq(productId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Product> findTop10ByUserWithDetails(User userParam) {
        return queryFactory
                .select(product).distinct()
                .from(product)
                .leftJoin(product.user, user).fetchJoin()
                .leftJoin(product.category, category).fetchJoin()
                .leftJoin(product.images, productImage).fetchJoin()
                .where(product.user.eq((userParam)))
                .orderBy(product.createdAt.desc())
                .limit(10)
                .fetch();
    }
}
