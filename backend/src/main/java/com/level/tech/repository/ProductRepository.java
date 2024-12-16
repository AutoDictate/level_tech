package com.level.tech.repository;

import com.level.tech.entity.Category;
import com.level.tech.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE p.name = :productName " +
           "AND p.category = :category")
    Optional<Product> findByProductNameAndCategory(@Param("productName") String productName, @Param("category") Category category);

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
}
