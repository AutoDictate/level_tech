package com.level.tech.repository;

import com.level.tech.entity.Category;
import com.level.tech.entity.MasterProduct;
import com.level.tech.entity.Model;
import com.level.tech.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MasterProductRepository extends JpaRepository<MasterProduct, Long> {

    @Query("SELECT mp FROM MasterProduct mp " +
           "JOIN mp.category c " +
           "JOIN mp.product p " +
           "JOIN mp.model m " +
           "WHERE LOWER(mp.hsnAcs) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(mp.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR CAST(mp.tax AS string) LIKE CONCAT('%', :search, '%') " +
           "OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<MasterProduct> findAllMasterProducts(@Param("search") String search, Pageable pageable);

    boolean existsByCategoryAndProductAndModel(Category category, Product product, Model model);

    MasterProduct findByCategoryAndProductAndModel(Category category, Product product, Model model);

    Optional<MasterProduct> findByCategoryNameAndProductNameAndModelName(String categoryName, String productName, String modelName);
}
