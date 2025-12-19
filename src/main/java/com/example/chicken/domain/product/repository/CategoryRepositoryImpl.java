package com.example.chicken.domain.product.repository;

import static com.example.chicken.domain.product.entity.QCategory.category;
import static com.example.chicken.domain.product.entity.QProduct.product;

import com.example.chicken.common.util.QueryDslUtil;
import com.example.chicken.domain.product.dto.CategoryCountResponseDto;
import com.example.chicken.domain.product.entity.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CategoryCountResponseDto> searchCategoriesWithCount(Pageable pageable) {

        PathBuilder<Category> pathBuilder = new PathBuilder<>(category.getType(), category.getMetadata());

        List<CategoryCountResponseDto> result = queryFactory
                .select(Projections.constructor(CategoryCountResponseDto.class,
                        category.id,
                        category.code,
                        category.name,
                        category.createdAt,
                        category.modifiedAt,
                        product.count()))
                .from(category)
                .leftJoin(category.products, product)
                .groupBy(category.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(category.count())
                .from(category);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}
