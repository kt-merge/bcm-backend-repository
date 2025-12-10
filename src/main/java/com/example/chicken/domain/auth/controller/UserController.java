package com.example.chicken.domain.auth.controller;

import static com.example.chicken.common.constant.PathConstant.User.ME;
import static com.example.chicken.common.constant.PathConstant.User.MY_PRODUCTS;
import static com.example.chicken.common.constant.PathConstant.User.USER_PREFIX;

import com.example.chicken.domain.auth.dto.user.UpdateUserInfoDto;
import com.example.chicken.domain.auth.service.UserService;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.service.ProductService;
import com.example.chicken.dto.UserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PREFIX)
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping(ME)
    public ResponseEntity<UserResponseDto> getUserInfo() {
        UserResponseDto result = this.userService.getUserInfo();
        return ResponseEntity.ok(result);
    }

    @PutMapping(ME)
    public ResponseEntity<UserResponseDto> updateUserInfo(@RequestBody UpdateUserInfoDto request) {
        UserResponseDto result = this.userService.updateMyUserInfo(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping(MY_PRODUCTS)
    public ResponseEntity<List<ProductResponseDto>> getUserProducts() {
        return ResponseEntity.ok(this.productService.getMyProducts());
    }

}
