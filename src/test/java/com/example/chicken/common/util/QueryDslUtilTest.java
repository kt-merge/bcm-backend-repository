package com.example.chicken.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class QueryDslUtilTest {
    @Test
    @DisplayName("단일 컬럼 오름차순 정렬 변환 테스트")
    void singleSortAsc() {
        // given
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "username"));

        // when
        OrderSpecifier<?>[] orders = QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder);

        // then
        assertThat(orders).hasSize(1);
        assertThat(orders[0].getOrder()).isEqualTo(Order.ASC);
        assertThat(orders[0].getTarget().toString()).hasToString("member.username");
    }

    @Test
    @DisplayName("단일 컬럼 내림차순 정렬 변환 테스트")
    void singleSortDesc() {
        // given
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "age"));

        // when
        OrderSpecifier<?>[] orders = QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder);

        // then
        assertThat(orders).hasSize(1);
        assertThat(orders[0].getOrder()).isEqualTo(Order.DESC);
        assertThat(orders[0].getTarget().toString()).hasToString("member.age");
    }

    @Test
    @DisplayName("다중 컬럼 정렬 변환 테스트 (이름 ASC, 나이 DESC)")
    void multiSort() {
        // given
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");
        Sort sort = Sort.by(
                Sort.Order.asc("username"),
                Sort.Order.desc("age")
        );
        Pageable pageable = PageRequest.of(0, 10, sort);

        // when
        OrderSpecifier<?>[] orders = QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder);

        // then
        assertThat(orders).hasSize(2);

        assertThat(orders[0].getOrder()).isEqualTo(Order.ASC);
        assertThat(orders[0].getTarget().toString()).hasToString("member.username");

        assertThat(orders[1].getOrder()).isEqualTo(Order.DESC);
        assertThat(orders[1].getTarget().toString()).hasToString("member.age");
    }

    @Test
    @DisplayName("정렬 조건이 없을 경우 빈 배열 반환")
    void noSort() {
        // given
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");
        Pageable pageable = PageRequest.of(0, 10); // 정렬 없음

        // when
        OrderSpecifier<?>[] orders = QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder);

        // then
        assertThat(orders)
                .isNotNull()
                .isEmpty();
    }

    // 테스트용 더미 클래스 (실제 엔티티가 있다면 불필요)
    static class Member {
        String username;
        int age;
    }

}
