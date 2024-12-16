package com.level.tech.service;

import com.level.tech.dto.PurchaseDetailsDTO;
import com.level.tech.dto.request.PurchaseDetailRequest;

import java.util.List;

public interface PurchaseDetailsService {

    PurchaseDetailsDTO addPurchaseDetails(PurchaseDetailRequest request, Long purchaseId);

    PurchaseDetailsDTO updatePurchaseDetails(Long id, PurchaseDetailRequest request);

    PurchaseDetailsDTO getPurchaseDetail(Long id);

    List<PurchaseDetailsDTO> getPurchaseDetails();

    void deletePurchaseDetail(Long id);

    void deletePurchaseDetails(List<Long> ids);

}
