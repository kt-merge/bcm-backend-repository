package com.example.chicken.domain.product.repository;

import static com.example.chicken.domain.product.entity.QProductBid.productBid;

import com.example.chicken.domain.admin.dto.DailyBidRegistrationCountDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductBidRepositoryImpl implements ProductBidRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyBidRegistrationCountDto> countBidsByDay(LocalDateTime startDate) {
        DateTemplate<LocalDate> formattedDate = Expressions.dateTemplate(LocalDate.class,
                "CAST({0} AS localDate)",
                productBid.createdAt);

        return queryFactory
                .select(Projections.constructor(DailyBidRegistrationCountDto.class, formattedDate,
                        productBid.id.count()))
                .from(productBid)
                .where(productBid.createdAt.goe(startDate))
                .groupBy(formattedDate)
                .orderBy(formattedDate.asc())
                .fetch();
    }
}
