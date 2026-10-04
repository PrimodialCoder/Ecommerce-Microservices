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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final String uplaodDir = System.getProperty("user.dir")+ "uploads/products/";


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

    @Override
    public ProductDto uplaodImage(Long prductId, MultipartFile file) throws IOException {
        Product existingProduct = productRepository.findById(prductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + prductId));

        if(file.isEmpty()) {
            throw new RuntimeException("Image File is empty");
        }

        long maxFileSize = 2 * 1024 * 1024; // 2MB
        if(file.getSize() > maxFileSize) {
            throw new RuntimeException("Image File is too large. Maximum allowed size is 2MB");
        }

        List<String> allowedExtensions = Arrays.asList("image/jpeg", "image/png", "image/jpg");
        //mime type check
        if(!allowedExtensions.contains(file.getContentType())) {
            throw new RuntimeException("Invalid file type. Only JPEG, PNG and JPG are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".")+1).toLowerCase();

        List<String> allowedExt = List.of("jpeg", "png", "jpg");
        if(!allowedExt.contains(ext)) {
            throw new RuntimeException("Invalid file extension. Only JPEG, PNG and JPG are allowed");
        }

        File folder = new File(uplaodDir);

        if(!folder.exists()) {
            folder.mkdirs();
        }

        String newFileName = UUID.randomUUID().toString() + "." + ext;
        Path filePath = Paths.get(uplaodDir + newFileName);
        Files.write(filePath, file.getBytes());

        String imageUrl = "/uploads/products/" + newFileName;
        existingProduct.setImageUrl(imageUrl);
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toDto(updatedProduct);

    }
    // Implement the methods defined in ProductService interface
}
