package com.example.chicken.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class QueryDslUtil {

    private QueryDslUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, PathBuilder<?> pathBase) {
        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[0];
        }

        ArrayList<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(direction, pathBase.get(order.getProperty()));
            orders.add(orderSpecifier);
        }

        return orders.toArray(new OrderSpecifier[0]);
    }
}
