package com.level.tech.controller;

import com.level.tech.dto.SaleDTO;
import com.level.tech.dto.request.SaleRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDTO> addSale(
            @RequestBody @Valid SaleRequest request
    ) {
        return new ResponseEntity<>(
                saleService.addSale(request), HttpStatus.CREATED);
    }

    @PutMapping("/{saleId}")
    public ResponseEntity<SaleDTO> updateSale(
            @PathVariable(name = "saleId") Long saleId,
            @RequestBody @Valid SaleRequest request
    ) {
        return new ResponseEntity<>(
                saleService.updateSale(saleId, request), HttpStatus.OK);
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleDTO> getSaleById(
            @PathVariable(name = "saleId") Long saleId
    ) {
        return new ResponseEntity<>(
                saleService.getSale(saleId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedResponseDTO> getAllSales(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "dateFilterBy", required = false, defaultValue = "createdAt") String dateFilterBy
    ) {
        if (pageNo != null && count != null && pageNo > 0) {
            pageNo = pageNo - 1;
        }
        return new ResponseEntity<>(
                saleService.getAllSales(pageNo, count, sortBy, orderBy, search, startDate, endDate, dateFilterBy), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteSales(
            @RequestBody List<Long> saleIds
    ) {
        saleService.deleteSales(saleIds);
        return new ResponseEntity<>(
                new ResponseData("Sale's deleted successfully") , HttpStatus.OK);
    }

}
