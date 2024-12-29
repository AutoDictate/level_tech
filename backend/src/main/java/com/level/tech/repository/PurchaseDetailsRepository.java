package com.level.tech.repository;

import com.level.tech.entity.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Long> {

    @Query("SELECT pd " +
           "FROM PurchaseDetails pd " +
           "WHERE pd.isDeleted = false")
    List<PurchaseDetails> findAllByActive();
}
