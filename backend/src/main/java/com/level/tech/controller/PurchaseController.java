package com.level.tech.controller;

import com.level.tech.dto.PurchaseDTO;
import com.level.tech.dto.request.PurchaseRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseDTO> addPurchase(
            @RequestBody @Valid PurchaseRequest request
    ) {
        return new ResponseEntity<>(
                purchaseService.addPurchase(request), HttpStatus.CREATED);
    }

    @PutMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDTO> updatePurchase(
            @PathVariable(name = "purchaseId") Long purchaseId,
            @RequestBody @Valid PurchaseRequest request
    ) {
        return new ResponseEntity<>(
                purchaseService.updatePurchase(purchaseId, request), HttpStatus.OK);
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseDTO> getPurchaseById(
            @PathVariable(name = "purchaseId") Long purchaseId
    ) {
        return new ResponseEntity<>(
                purchaseService.getPurchase(purchaseId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedResponseDTO> getAllPurchases(
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
                purchaseService.getAllPurchases(pageNo, count, sortBy, orderBy, search, startDate, endDate, dateFilterBy), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deletePurchases(
            @RequestBody List<Long> purchaseIds
    ) {
        purchaseService.deletePurchases(purchaseIds);
        return new ResponseEntity<>(
                new ResponseData("Purchase's deleted successfully") , HttpStatus.OK);
    }

}
