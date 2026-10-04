package com.ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be greater than 0")
    private Double price;
    private Double discountPrice;
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    private String brand;
    private String imageUrl;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
