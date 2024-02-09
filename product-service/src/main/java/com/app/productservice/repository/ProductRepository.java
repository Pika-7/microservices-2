package com.app.productservice.repository;

import com.app.productservice.entity.Category;
import com.app.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductName(String productName);

    List<Product> findByCategory(Category category);
}
