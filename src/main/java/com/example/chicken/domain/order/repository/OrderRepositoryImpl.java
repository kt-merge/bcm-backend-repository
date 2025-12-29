package com.example.chicken.domain.order.repository;

import static com.example.chicken.domain.auth.entity.user.QUser.user;
import static com.example.chicken.domain.order.entity.QOrder.order;
import static com.example.chicken.domain.product.entity.QProduct.product;
import static com.example.chicken.domain.product.entity.QProductImage.productImage;

import com.example.chicken.common.util.QueryDslUtil;
import com.example.chicken.domain.order.dto.OrderSearchCondition;
import com.example.chicken.domain.order.entity.Order;
import com.example.chicken.domain.order.entity.OrderStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> searchOrders(OrderSearchCondition condition, Pageable pageable) {

        PathBuilder<Order> pathBuilder = new PathBuilder<>(order.getType(), order.getMetadata());

        Predicate[] searchConditions = {
                equalsOrderStatus(condition.orderStatus()),
                equalsUserId(condition.userId())
        };

        List<Order> result = queryFactory
                .select(order)
                .from(order)
                .leftJoin(order.product, product).fetchJoin()
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.product.images, productImage).fetchJoin()
                .where(searchConditions)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder))
                .fetch();

        JPAQuery<Long> countQuery;
        countQuery = queryFactory
                .select(order.count())
                .from(order)
                .where(searchConditions);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }


    private static Predicate equalsOrderStatus(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;
    }

    private static Predicate equalsUserId(Long userId) {
        return userId != null ? order.user.id.eq(userId) : null;
    }

}
