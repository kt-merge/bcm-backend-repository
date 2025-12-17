package com.example.chicken.domain.auth.repository;

import static com.example.chicken.domain.auth.entity.user.QUser.user;

import com.example.chicken.domain.admin.dto.UserSearchCondition;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.entity.user.UserStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> searchUsers(UserSearchCondition condition, Pageable pageable) {

        Predicate[] searchConditions = {
                hasUserIdContaining(condition.userId()),
                hasNameContaining(condition.nickname()),
                hasEmailContaining(condition.email()),
                hasPhoneNumberContaining(condition.phoneNumber()),
                equalsUserStatus(condition.userStatus()),
                equalsRole(condition.role())
        };

        List<User> result = queryFactory
                .select(user)
                .from(user)
                .where(searchConditions)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(searchConditions);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private static Predicate hasUserIdContaining(Long userId) {
        return userId != null ? user.id.stringValue().contains(userId.toString()) : null;
    }

    private static Predicate hasNameContaining(String nickname) {
        return nickname != null ? user.nickname.contains(nickname) : null;
    }

    private static Predicate hasEmailContaining(String email) {
        return email != null ? user.email.contains(email) : null;
    }

    private static Predicate hasPhoneNumberContaining(String phoneNumber) {
        return phoneNumber != null ? user.phoneNumber.contains(phoneNumber) : null;
    }

    private static Predicate equalsUserStatus(UserStatus userStatus) {
        return userStatus != null ? user.status.eq(userStatus) : null;
    }

    private static Predicate equalsRole(Role role) {
        return role != null ? user.role.eq(role) : null;
    }


}
