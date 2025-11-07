package com.example.chicken.domain;

import com.example.chicken.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Builder
    private User (String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public static User from(UserRequestDto request) {
        return User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(request.password())
                .build();
    }

}
