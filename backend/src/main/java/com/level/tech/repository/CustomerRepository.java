package com.level.tech.repository;

import com.level.tech.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    @Query("""
                SELECT c\s
                FROM Customer c
                WHERE (:search IS NULL\s
                       OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))\s
                       OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))\s
                       OR LOWER(c.city) LIKE LOWER(CONCAT('%', :search, '%')))
                AND c.isDeleted = false
           \s""")
    Page<Customer> findAllCustomers(@Param("search") String search, Pageable pageable);

    @Query("SELECT c " +
           "FROM Customer c " +
           "WHERE c.isDeleted = false")
    List<Customer> findAllByActive();

    @Query("SELECT c " +
           "FROM Customer c " +
           "WHERE c.isDeleted = false")
    Page<Customer> findAllByActive(Pageable pageable);
}
