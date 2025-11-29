package com.example.chicken.domain.auth.entity.user;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.domain.auth.dto.user.UpdateUserInfoDto;

import jakarta.persistence.*;
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

	private String phoneNumber;

	@Builder
	private User(String nickname, String email, String password, Role role, String phoneNumber) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.role = role;
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
			.phoneNumber(request.phoneNumber())
			.build();
	}

	public void updateUserInfo(UpdateUserInfoDto request) {
		this.nickname = request.nickname();
		this.phoneNumber = request.phoneNumber();
	}

	public void updatePassword(String encodedPassword) {
		this.password = encodedPassword;
	}
}
