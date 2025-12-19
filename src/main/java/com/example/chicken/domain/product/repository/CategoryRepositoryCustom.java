package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.product.dto.CategoryCountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<CategoryCountResponseDto> searchCategoriesWithCount(Pageable pageable);

}
