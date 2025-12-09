package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_PRODUCTS_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_PRODUCTS_PREFIX;

import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.dto.ProductUpdateRequestDto;
import com.example.chicken.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_PRODUCTS_PREFIX)
public class AdminProductController {

    private final ProductService productService;

    @PatchMapping(ADMIN_PRODUCTS_ID)
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId,
                                                            @RequestBody @Valid ProductUpdateRequestDto request) {
        ProductResponseDto result = this.productService.updateProduct(productId, request);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(ADMIN_PRODUCTS_ID)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(null);
    }

}
