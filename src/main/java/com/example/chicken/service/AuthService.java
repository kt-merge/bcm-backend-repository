package com.example.chicken.service;

import com.example.chicken.domain.User;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(UserRequestDto request) {
        if(this.userRepository.findByEmail(request.email()).isPresent())
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");

        User user = this.userRepository.save(User.from(request));

        String encodedPassword = this.passwordEncoder.encode(request.password());

        user.encodePassword(encodedPassword);

        return UserResponseDto.from(user);
    }

}
