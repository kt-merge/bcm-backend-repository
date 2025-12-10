package com.example.chicken.domain.product.service;

import com.example.chicken.domain.product.dto.CategoryRequestDto;
import com.example.chicken.domain.product.dto.CategoryResponseDto;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.exception.CategoryAlreadyExists;
import com.example.chicken.domain.product.exception.CategoryNotFoundException;
import com.example.chicken.domain.product.exception.ExistsCategoryInProduct;
import com.example.chicken.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        Boolean isExistsCategory = this.categoryRepository.existsByCode(request.code());
        Boolean isExistsName = this.categoryRepository.existsByName(request.name());

        if (isExistsCategory || isExistsName) {
            throw new CategoryAlreadyExists();
        }

        Category result = this.categoryRepository.save(categoryMapper.toEntity(request));

        return this.categoryMapper.toResponseDto(result);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getCategories(Pageable pageable) {
        Page<Category> categories = this.categoryRepository.findAll(pageable);

        return categories.map(this.categoryMapper::toResponseDto);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto request) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));

        category.updateCategory(request.code(), request.name());

        return this.categoryMapper.toResponseDto(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));

        if (!category.getProducts().isEmpty()) {
            throw new ExistsCategoryInProduct();
        }

        this.categoryRepository.deleteById(categoryId);
    }

}
