package com.Joseph.dreamShop.repository;

import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(Category category, String brand);

    List<Product> findByBrandAndName(String brand, String name);

    int countByBrandAndName(String brand, String name);
}
