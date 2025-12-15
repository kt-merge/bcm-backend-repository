package com.example.chicken.domain.product.service;

import com.example.chicken.domain.product.dto.ProductImageResponseDto;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImage toEntity(Product product, String imageUrl) {
        return ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .build();
    }

    public ProductImageResponseDto toResponseDto(ProductImage productImage) {
        return ProductImageResponseDto.builder()
                .id(productImage.getId())
                .imageUrl(productImage.getImageUrl())
                .build();
    }

}
