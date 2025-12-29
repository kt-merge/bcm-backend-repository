package com.example.chicken.domain.announcement.repository;

import static com.example.chicken.domain.announcement.entity.QAnnouncement.announcement;

import com.example.chicken.common.util.QueryDslUtil;
import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import com.example.chicken.domain.announcement.entity.Announcement;
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
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AnnouncementResponseDto> searchAnnouncements(Pageable pageable) {

        PathBuilder<Announcement> pathBuilder = new PathBuilder<>(announcement.getType(), announcement.getMetadata());

        List<AnnouncementResponseDto> result = queryFactory
                .select(Projections.constructor(AnnouncementResponseDto.class,
                        announcement.announcementId,
                        announcement.title,
                        announcement.content,
                        announcement.category,
                        announcement.createdBy,
                        announcement.createdAt,
                        announcement.modifiedAt))
                .from(announcement)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, pathBuilder))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(announcement.count())
                .from(announcement);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}
