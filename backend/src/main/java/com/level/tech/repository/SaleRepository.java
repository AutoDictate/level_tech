package com.level.tech.repository;

import com.level.tech.entity.Sale;
import com.level.tech.enums.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s " +
           "WHERE LOWER(s.customer.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.invoiceNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.gstNo) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Sale> findAllSales(@Param("search") String search, Pageable pageable);

    @Query("SELECT s FROM Sale s " +
           "WHERE (:search IS NULL OR LOWER(s.customer.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND ((:dateFilterBy = 'billDate' AND s.billDate BETWEEN :start AND :end) " +
           "OR (:dateFilterBy = 'deliveryDate' AND s.deliveryDate BETWEEN :start AND :end) " +
           "OR (:dateFilterBy = 'paidDate' AND s.paidDate BETWEEN :start AND :end))")
    Page<Sale> findAllByDateRangeAndField(@Param("search") String search,
                                          @Param("start") LocalDate start,
                                          @Param("end") LocalDate end,
                                          @Param("dateFilterBy") String dateFilterBy,
                                          Pageable pageable);

    @Query("SELECT si.masterProduct.id, SUM(si.quantity) " +
           "FROM Sale s " +
           "JOIN s.saleItems si " +
           "WHERE s.dispatchCity = :dispatchCity " +
           "GROUP BY si.masterProduct.id")
    List<Object[]> getTotalSaleQuantities(@Param("dispatchCity") Branch branch);

    @Query("SELECT s " +
           "FROM Sale s " +
           "WHERE s.isDeleted = false")
    List<Sale> findAllByActive();

    @Query("SELECT s " +
           "FROM Sale s " +
           "WHERE s.isDeleted = false")
    Page<Sale> findAllByActive(Pageable pageable);
}
