package com.level.tech.service;

import com.level.tech.dto.SaleItemDTO;
import com.level.tech.dto.request.SaleItemRequest;

import java.util.List;

public interface SaleItemService {

    SaleItemDTO addSaleItem(SaleItemRequest request);

    SaleItemDTO updateSaleItem(Long id, SaleItemRequest request);

    SaleItemDTO getSaleItem(Long id);

    List<SaleItemDTO> getSaleItems();

    void deleteSaleItem(Long id);

    void deleteSaleItems(List<Long> ids);

}
