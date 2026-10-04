package com.ecommerce.product.reporitory;

import com.ecommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     void deleteById(Long id);

     //Advanced search queries
     Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
     Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);
     Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
     Page<Product> findByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);

     // Custom query for filtering products based on multiple criteria : keyord in name or description.
     @Query("SELECT p from Product p " +
             "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
     "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     Page<Product> filterProductByKeywordInNameOrDescription(@Param("keyword") String keyword, Pageable pageable);


// Custom query for advanced filtering based on multiple criteria: keyword in name or description, price range, and category.
     @Query("SELECT p from Product  p "+
     "WHERE (:Keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :Keyword, '%')) " +
             "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :Keyword, '%'))) " +
             "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
             "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
             "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
     Page<Product> advanceProductFilterByKeywordInNameOrDescriptionPriceRangeAndCategory(@Param("Keyword") String keyword,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("categoryId") Long categoryId,
                                          Pageable pageable);
}
