package com.level.tech.service;

import com.level.tech.dto.PurchaseDTO;
import com.level.tech.dto.request.PurchaseRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {

    PurchaseDTO addPurchase(PurchaseRequest request);

    PurchaseDTO updatePurchase(Long id, PurchaseRequest request);

    PurchaseDTO getPurchase(Long id);

    List<PurchaseDTO> getPurchases();

    PagedResponseDTO getAllPurchases(Integer pageNo, Integer count, String sortBy, String orderBy, String search, LocalDate startDate, LocalDate endDate, String dateFilterBy);

    void deletePurchase(Long id);

    void deletePurchases(List<Long> ids);

}
