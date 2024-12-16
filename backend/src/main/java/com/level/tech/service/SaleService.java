package com.level.tech.service;

import com.level.tech.dto.SaleDTO;
import com.level.tech.dto.request.SaleRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {

    SaleDTO addSale(SaleRequest request);

    SaleDTO updateSale(Long saleId, SaleRequest request);

    SaleDTO getSale(Long saleId);

    List<SaleDTO> getSales();

    PagedResponseDTO getAllSales(Integer pageNo, Integer count, String sortBy, String orderBy, String search, LocalDate startDate, LocalDate endDate, String dateFilterBy);

    void deleteSale(Long saleId);

    void deleteSales(List<Long> saleIds);
}
