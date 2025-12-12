package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_USERS_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_USERS_PREFIX;

import com.example.chicken.domain.admin.dto.UpdateUserInfoByAdminDto;
import com.example.chicken.domain.admin.dto.UserSearchCondition;
import com.example.chicken.domain.auth.service.UserService;
import com.example.chicken.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_USERS_PREFIX)
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsers(UserSearchCondition condition, Pageable pageable) {

        Page<UserResponseDto> result = this.userService.getUsers(condition, pageable);

        return ResponseEntity.ok(result);
    }

    @PatchMapping(ADMIN_USERS_ID)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,
                                                      @RequestBody @Valid UpdateUserInfoByAdminDto request) {
        UserResponseDto result = this.userService.updateUserInfo(userId, request);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(ADMIN_USERS_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
