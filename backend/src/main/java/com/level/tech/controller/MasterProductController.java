package com.level.tech.controller;

import com.level.tech.dto.MasterProductDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.MasterProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/master/product")
public class MasterProductController {

    private final MasterProductService masterProductService;

    @PostMapping
    public ResponseEntity<MasterProductDTO> addMasterProduct(
            @RequestBody @Valid MasterProductRequest request
    ) {
        return new ResponseEntity<>(
                masterProductService.addMasterProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<MasterProductDTO> updateMasterProduct(
            @PathVariable(name = "productId") Long productId,
            @RequestBody @Valid MasterProductRequest request
    ) {
        return new ResponseEntity<>(
                masterProductService.updateMasterProduct(productId, request), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<MasterProductDTO> getMasterProductById(
            @PathVariable(name = "productId") Long productId
    ) {
        return new ResponseEntity<>(
                masterProductService.getMasterProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MasterProductDTO>> getAllMasterProducts() {
        return new ResponseEntity<>(
                masterProductService.getAllMasterProducts(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedResponseDTO> getAllMasterProducts(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
            @RequestParam(name = "search", required = false) String search
    ) {
        if (pageNo != null && count != null && pageNo > 0) {
            pageNo = pageNo - 1;
        }
        return new ResponseEntity<>(
                masterProductService.getAllMasterProducts(pageNo, count, sortBy, orderBy, search), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteMasterProduct(
            @RequestBody List<Long> productIds
    ) {
        masterProductService.deleteMasterProducts(productIds);
        return new ResponseEntity<>(
                new ResponseData("Products deleted successfully") , HttpStatus.OK);
    }

}
