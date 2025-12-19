package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_CATEGORIES_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_CATEGORIES_PREFIX;

import com.example.chicken.domain.product.dto.CategoryCountResponseDto;
import com.example.chicken.domain.product.dto.CategoryRequestDto;
import com.example.chicken.domain.product.dto.CategoryResponseDto;
import com.example.chicken.domain.product.service.CategoryQueryService;
import com.example.chicken.domain.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_CATEGORIES_PREFIX)
public class AdminCategoryController {

    private final CategoryQueryService categoryQueryService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryRequestDto request) {
        CategoryResponseDto result = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryCountResponseDto>> getCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryQueryService.getCategories(pageable));
    }

    @PatchMapping(ADMIN_CATEGORIES_ID)
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId,
                                                              @RequestBody @Valid CategoryRequestDto request) {
        CategoryResponseDto result = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(ADMIN_CATEGORIES_ID)
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
