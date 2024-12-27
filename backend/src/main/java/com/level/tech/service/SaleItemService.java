package com.level.tech.service;

import com.level.tech.dto.SaleItemDTO;
import com.level.tech.dto.request.SaleItemRequest;
import com.level.tech.entity.Sale;

import java.util.List;

public interface SaleItemService {

    SaleItemDTO addSaleItem(SaleItemRequest request, Sale saleId);

    SaleItemDTO updateSaleItem(Long id, SaleItemRequest request);

    SaleItemDTO getSaleItem(Long id);

    List<SaleItemDTO> getSaleItems();

    void deleteSaleItem(Long id);

    void deleteSaleItems(List<Long> ids);

}
