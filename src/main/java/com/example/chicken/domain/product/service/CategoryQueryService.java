package com.example.chicken.domain.product.service;

import com.example.chicken.domain.product.dto.CategoryCountResponseDto;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.exception.CategoryNotFoundException;
import com.example.chicken.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public Page<CategoryCountResponseDto> getCategories(Pageable pageable) {
        return categoryRepository.searchCategoriesWithCount(pageable);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
    }

}
