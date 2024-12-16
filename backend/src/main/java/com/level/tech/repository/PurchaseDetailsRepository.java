package com.level.tech.repository;

import com.level.tech.entity.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Long> {
}
