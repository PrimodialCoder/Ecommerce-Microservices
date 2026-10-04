package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.exception.ResourceAlreadyExistsException;
import com.ecommerce.product.exception.ResourceNotFoundException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.reporitory.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = null;
        if(productDto.getCategoryId() != null){
            category = new Category();
            category.setId(productDto.getCategoryId());

        };
        Product product = ProductMapper.toEntity(productDto, category);// Assuming category is handled elsewhere
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDiscountPrice(productDto.getDiscountPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setImageUrl(productDto.getImageUrl());

        Category category = existingProduct.getCategory();
        if (productDto.getCategoryId() != null) {
            if (category == null || !category.getId().equals(productDto.getCategoryId())) {
                category = new Category();
                category.setId(productDto.getCategoryId());
                existingProduct.setCategory(category);
            }
        } else {
            existingProduct.setCategory(null);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductMapper.toDto(existingProduct);
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.filterProductByKeywordInNameOrDescription(keyword, pageable);
        return productPage.map(ProductMapper::toDto);

    }

    @Override
    public Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.advanceProductFilterByKeywordInNameOrDescriptionPriceRangeAndCategory(null, minPrice, maxPrice, categoryId, pageable);
        return productPage.map(ProductMapper::toDto);
    }

    @Override
    public Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.advanceProductFilterByKeywordInNameOrDescriptionPriceRangeAndCategory(
                keyword,
                minPrice,
                maxPrice,
                categoryId,
                pageable).map(ProductMapper::toDto);
    }
    // Implement the methods defined in ProductService interface
}
