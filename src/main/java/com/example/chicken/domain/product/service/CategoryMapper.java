package com.example.chicken.domain.product.service;

import com.example.chicken.domain.product.dto.CategoryRequestDto;
import com.example.chicken.domain.product.dto.CategoryResponseDto;
import com.example.chicken.domain.product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto request) {
        return Category.builder()
                .code(request.code())
                .name(request.name())
                .build();
    }

    public CategoryResponseDto toResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .modifiedAt(category.getModifiedAt())
                .build();
    }

}
