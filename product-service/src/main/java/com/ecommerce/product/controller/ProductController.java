package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductDto> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam String keyword,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<ProductDto> products = productService.searchProducts(keyword, page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProducts(@RequestParam(required = false) Long categoryId,
                                                            @RequestParam(required = false) Double minPrice,
                                                            @RequestParam(required = false) Double maxPrice,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Page<ProductDto> products = productService.filterProducts(categoryId, minPrice, maxPrice, page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/advance-filter")
    public ResponseEntity<Page<ProductDto>> advanceFilter(@RequestParam(required = false) String keyword,
                                                           @RequestParam(required = false) Long categoryId,
                                                           @RequestParam(required = false) Double minPrice,
                                                           @RequestParam(required = false) Double maxPrice,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductDto> products = productService.advanceFilter(keyword, categoryId, minPrice, maxPrice, page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }


    @PostMapping("/{id}/upload-image")
    public ResponseEntity<ProductDto> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.uplaodImage(id, file));
    }


}
