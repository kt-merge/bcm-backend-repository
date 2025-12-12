package com.example.chicken.domain.product.repository;

import static com.example.chicken.domain.product.entity.QProduct.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.entity.BidStatus;
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

		Predicate[] searchConditions = {
			equalsId(condition.id()),
			hasNameContaining(condition.name()),
			equalsCategoryId(condition.categoryId()),
			equalsBidStatus(condition.bidStatus())
		};

		List<Product> result = queryFactory
			.select(product)
			.from(product)
			.where(searchConditions)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(product.count())
			.from(product)
			.where(searchConditions);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private static Predicate equalsId(Long id) {
		return id != null ? product.id.eq(id) : null;
	}

	private static Predicate hasNameContaining(String name) {
		return name != null ? product.name.contains(name) : null;
	}

	private static Predicate equalsCategoryId(Long categoryId) {
		return categoryId != null ? product.category.id.eq(categoryId) : null;
	}

	private static Predicate equalsBidStatus(BidStatus bidStatus) {
		return bidStatus != null ? product.bidStatus.eq(bidStatus) : null;
	}

}
