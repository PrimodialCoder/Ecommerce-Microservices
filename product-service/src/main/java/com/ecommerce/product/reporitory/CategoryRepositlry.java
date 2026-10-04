package com.ecommerce.product.reporitory;

import com.ecommerce.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepositlry extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long parentId);
    Boolean existsByName(String name);

}
