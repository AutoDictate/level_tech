package com.level.tech.repository;

import com.level.tech.entity.Model;
import com.level.tech.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    boolean existsByNameAndProduct(String modelName, Product product);

    @Query("SELECT m " +
           "FROM Model m " +
           "WHERE m.name = :name " +
           "AND m.product.name = :product " +
           "AND m.product.category.name = :category")
    Optional<Model> findByNameAndProductAndCategory(
            @Param("name") String name,
            @Param("product") String product,
            @Param("category") String category
    );

    @Query("SELECT m " +
           "FROM Model m " +
           "WHERE m.product.id = :productId")
    List<Model> findAllByProductId(@Param("productId") Long productId);
}
