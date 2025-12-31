package com.example.chicken.domain.announcement.repository;

import static com.example.chicken.domain.announcement.entity.QAnnouncement.announcement;

import com.example.chicken.common.util.QueryDslUtil;
import com.example.chicken.domain.announcement.entity.Announcement;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Announcement> searchAnnouncements(String keyword, Pageable pageable) {

        PathBuilder<Announcement> pathBuilder = new PathBuilder<>(announcement.getType(), announcement.getMetadata());

        List<Announcement> result = queryFactory
                .selectFrom(announcement)
                .where(containsKeyword(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(announcement.count())
                .from(announcement)
                .where(containsKeyword(keyword));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return announcement.title.containsIgnoreCase(keyword)
                .or(announcement.content.containsIgnoreCase(keyword));
    }
}
