package com.example.chicken.domain.auth.repository;

import com.example.chicken.domain.admin.dto.DailyUserRegistrationCountDto;
import com.example.chicken.domain.auth.entity.user.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

        @Query(value = "SELECT * FROM users WHERE delete_status = 'DELETED'",
                        countQuery = "SELECT count(*) FROM users WHERE delete_status = 'DELETED'",
                        nativeQuery = true)
        Page<User> findDeletedUsers(Pageable pageable);

    @Query("SELECT new com.example.chicken.domain.admin.dto.DailyUserRegistrationCountDto(CAST(user.createdAt As localdate), COUNT(*)) "
            + "FROM User user "
            + "WHERE user.createdAt >= :startDate AND user.createdAt < :endDate "
            + "GROUP BY CAST(user.createdAt As localdate) "
            + "ORDER BY CAST(user.createdAt As localdate) DESC")
    List<DailyUserRegistrationCountDto> countUsersByDay(@Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);
}
