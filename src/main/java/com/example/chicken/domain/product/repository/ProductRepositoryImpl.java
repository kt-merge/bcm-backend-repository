package com.example.chicken.domain.product.repository;

import static com.example.chicken.domain.product.entity.QProduct.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.entity.Product;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {

		String name = condition.name();

		List<Product> result = queryFactory
			.select(product)
			.from(product)
			.where(
				hasNameContaining(name)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1L)
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

}
