package com.level.tech.repository;

import com.level.tech.entity.Purchase;
import com.level.tech.enums.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("""
            SELECT p FROM Purchase p
            WHERE (:search IS NULL OR LOWER(p.orderNo) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (
                   (:dateFilterBy = 'createdAt' AND p.createdAt BETWEEN :start AND :end)
                OR (:dateFilterBy = 'orderDate' AND p.orderDate BETWEEN :start AND :end)
                OR (:dateFilterBy = 'invoiceDate' AND p.invoiceDate BETWEEN :start AND :end))""")
    Page<Purchase> findAllByDateRangeAndField(
            @Param("search") String search,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("dateFilterBy") String dateFilterBy,
            Pageable pageable
    );


    @Query("""
    SELECT p FROM Purchase p
    WHERE LOWER(p.orderNo) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.gstNo) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.invoiceNo) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.modeOfDispatch) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.transporter) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.lrRrNo) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(p.vendor.name) LIKE LOWER(CONCAT('%', :search, '%'))""")
    Page<Purchase> findAllPurchases(@Param("search") String search, Pageable pageable);

    @Query("SELECT pd.masterProduct.id, SUM(pd.quantity) " +
           "FROM Purchase p " +
           "JOIN p.purchaseDetails pd " +
           "WHERE p.consignedTo = :branch " +
           "GROUP BY pd.masterProduct.id")
    List<Object[]> getTotalPurchaseQuantities(@Param("branch") Branch branch);

    @Query("SELECT p " +
           "FROM Purchase p " +
           "JOIN p.purchaseDetails pd " +
           "WHERE pd.masterProduct.id = :productId " +
           "AND p.consignedTo = :branch")
    List<Purchase> findPurchasesByProduct(@Param("productId") Long productId, @Param("branch") Branch branch);

}
