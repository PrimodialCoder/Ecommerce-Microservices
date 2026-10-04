package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir);

    Page<ProductDto> searchProducts(String keyword, int page, int size);

    Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size);

    Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir);

    ProductDto uplaodImage(Long prductId, MultipartFile file) throws IOException;
}
