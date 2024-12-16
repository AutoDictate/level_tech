package com.level.tech.repository;

import com.level.tech.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("SELECT v FROM Vendor v " +
           "WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(v.phoneNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(v.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(v.ifscCode) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(v.bankBranch) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Vendor> findAllVendors(@Param("search") String search, Pageable pageable);


}
