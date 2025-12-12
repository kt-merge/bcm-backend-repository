package com.example.chicken.domain.auth.service;

import com.example.chicken.domain.admin.dto.DailyUserRegistrationCountDto;
import com.example.chicken.domain.admin.dto.UpdateUserInfoByAdminDto;
import com.example.chicken.domain.admin.dto.UserSearchCondition;
import com.example.chicken.domain.admin.exception.WhyDeleteMeException;
import com.example.chicken.domain.auth.dto.user.UpdateUserInfoDto;
import com.example.chicken.domain.auth.dto.user.WinnerResponseDto;
import com.example.chicken.domain.auth.entity.token.ResetPasswordToken;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.ResetTokenExpiredException;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.ResetPasswordTokenRepository;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.order.dto.OrderResponseDto;
import com.example.chicken.domain.order.repository.OrderRepository;
import com.example.chicken.domain.order.service.OrderMapper;
import com.example.chicken.domain.product.dto.ProductBidResponseDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.repository.HighestBidderRepository;
import com.example.chicken.domain.product.repository.ProductBidRepository;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.domain.product.service.CategoryMapper;
import com.example.chicken.domain.product.service.ProductMapper;
import com.example.chicken.dto.UserResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductBidRepository productBidRepository;
    private final HighestBidderRepository highestBidderRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo() {
        User user = getUser();

        List<WinnerResponseDto> winnerResponse = this.highestBidderRepository.findByWinnerId(user.getId())
                .stream().map(WinnerResponseDto::from)
                .toList();

        List<ProductBidResponseDto> productBids = this.productBidRepository.findDistinctByUserId(user.getId())
                .stream()
                .filter(ProductBid::isHighestBidderNotExists)
                .map(ProductBidResponseDto::from)
                .toList();

        List<ProductResponseDto> productResponse = this.productRepository.findByUser(user)
                .stream()
                .map((product) -> productMapper.toResponseDto(product, userMapper.toResponse(user),
                        categoryMapper.toResponseDto(product.getCategory())))
                .toList();

        List<OrderResponseDto> orderResponse = this.orderRepository.findByUser(user)
                .stream().map(orderMapper::toDto)
                .toList();

        return userMapper.toResponse(user, winnerResponse, productBids, productResponse, orderResponse);
    }

    @Transactional
    public UserResponseDto updateUserInfo(Long userId, UpdateUserInfoByAdminDto request) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId.toString())
        );

        user.updateUserInfoByAdmin(request);

        return userMapper.toResponse(this.userRepository.save(user));
    }

    @Transactional
    public UserResponseDto updateMyUserInfo(UpdateUserInfoDto request) {
        User user = getUser();

        user.updateUserInfo(request);

        return userMapper.toResponse(this.userRepository.save(user));
    }

    private User getUser() {
        String email =
                SecurityContextHolder.getContext().getAuthentication().getName();

        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsers(UserSearchCondition condition, Pageable pageable) {
        Page<User> users = this.userRepository.searchUsers(condition, pageable);

        return users.map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<DailyUserRegistrationCountDto> getDailyUserRegistrationCounts(Integer daysAgo) {
        LocalDateTime startDate = LocalDate.now().minusDays(daysAgo - 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().plusDays(1).atStartOfDay();

        return this.userRepository.countUsersByDay(startDate, endDate);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if (user.getRole().equals(Role.ADMIN)) {
            throw new WhyDeleteMeException();
        }

        this.userRepository.delete(user);
    }

    @Transactional
    public void updatePassword(String password, String token) {
        ResetPasswordToken resetPasswordToken = this.resetPasswordTokenRepository
                .findByResetToken(token).orElseThrow(ResetTokenExpiredException::new);

        String email = resetPasswordToken.getId();

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        user.updatePassword(this.passwordEncoder.encode(password));

        this.resetPasswordTokenRepository.delete(resetPasswordToken);
    }

}
