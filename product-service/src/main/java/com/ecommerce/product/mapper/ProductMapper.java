package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .quantity(product.getQuantity())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public static Product toEntity(ProductDto productDto, Category category) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .discountPrice(productDto.getDiscountPrice())
                .quantity(productDto.getQuantity())
                .brand(productDto.getBrand())
                .imageUrl(productDto.getImageUrl())
                .category(category)
                .build();
    }
}
