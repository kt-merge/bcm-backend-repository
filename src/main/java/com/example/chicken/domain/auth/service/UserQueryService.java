package com.example.chicken.domain.auth.service;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.UserAlreadyExists;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public boolean isUserExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public void checkUserExistsByEmail(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new UserAlreadyExists();
        }
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

}
