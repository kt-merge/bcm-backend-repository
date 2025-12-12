package com.example.chicken.domain.auth.repository;

import com.example.chicken.domain.admin.dto.UserSearchCondition;
import com.example.chicken.domain.auth.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> searchUsers(UserSearchCondition condition, Pageable pageable);

}
