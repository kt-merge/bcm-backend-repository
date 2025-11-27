package com.example.chicken.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
	@NotBlank @Email String email, @NotBlank @Size(min = 8) String password, String nickname, String phoneNumber) {

	public static UserRequestDto newInstance() {
		return new UserRequestDto("", "", "", "");
	}

}