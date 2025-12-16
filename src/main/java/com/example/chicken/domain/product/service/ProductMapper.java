package com.example.chicken.domain.product.service;

import com.example.chicken.common.entity.DeleteStatus;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.dto.CategoryResponseDto;
import com.example.chicken.domain.product.dto.ProductBidInfoResponseDto;
import com.example.chicken.domain.product.dto.ProductImageResponseDto;
import com.example.chicken.domain.product.dto.ProductRequestDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.dto.UserResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto request, User user, Category category) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .category(category)
                .startPrice(request.price())
                .bidPrice(request.price())
                .bidCount(0L)
                .deleteStatus(DeleteStatus.ACTIVATED)
                .bidStatus(BidStatus.NOT_BIDDED)
                .productStatus(request.productStatus())
                .bidEndDate(request.bidEndDate())
                .user(user)
                .build();
    }

    public ProductResponseDto toResponseDto(Product product, UserResponseDto user, CategoryResponseDto category,
                                            List<ProductImageResponseDto> productImageResponseDtoList) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(category)
                .startPrice(product.getStartPrice())
                .bidPrice(product.getBidPrice())
                .bidCount(product.getBidCount())
                .bidStatus(product.getBidStatus())
                .productStatus(product.getProductStatus())
                .user(user)
                .imageUrls(productImageResponseDtoList)
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .bidEndDate(product.getBidEndDate())
                .productBids(new ArrayList<>())
                .build();
    }

    public ProductResponseDto toResponseDto(Product product, List<ProductBidInfoResponseDto> productBids,
                                            UserResponseDto user, CategoryResponseDto category,
                                            List<ProductImageResponseDto> productImageResponseDtoList) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(category)
                .startPrice(product.getStartPrice())
                .bidPrice(product.getBidPrice())
                .bidCount(product.getBidCount())
                .bidStatus(product.getBidStatus())
                .productStatus(product.getProductStatus())
                .user(user)
                .imageUrls(productImageResponseDtoList)
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .bidEndDate(product.getBidEndDate())
                .productBids(productBids)
                .build();
    }

}
