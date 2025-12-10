package com.example.chicken.domain.auth.entity.user;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.admin.dto.UpdateUserInfoByAdminDto;
import com.example.chicken.domain.auth.dto.user.UpdateUserInfoDto;
import com.example.chicken.dto.UserRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String phoneNumber;

    @Builder
    private User(String nickname, String email, String password, Role role, UserStatus status, String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public static User from(UserRequestDto request) {
        return User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .status(UserStatus.PENDING)
                .phoneNumber(request.phoneNumber())
                .build();
    }

    public void updateUserInfo(UpdateUserInfoDto request) {
        this.nickname = request.nickname();
        this.phoneNumber = request.phoneNumber();
    }

    public void updateUserInfoByAdmin(UpdateUserInfoByAdminDto request) {
        this.nickname = request.nickname();
        this.phoneNumber = request.phoneNumber();
        this.status = request.status();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
