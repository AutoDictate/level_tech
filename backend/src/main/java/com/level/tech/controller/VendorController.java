package com.level.tech.controller;

import com.level.tech.dto.VendorDTO;
import com.level.tech.dto.request.VendorRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorDTO> addVendor(
            @RequestBody @Valid VendorRequest request
    ) {
        return new ResponseEntity<>(
                vendorService.addVendor(request), HttpStatus.CREATED);
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> updateVendor(
            @PathVariable(name = "vendorId") Long vendorId,
            @RequestBody @Valid VendorRequest request
    ) {
        return new ResponseEntity<>(
                vendorService.updateVendor(vendorId, request), HttpStatus.OK);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendorById(
            @PathVariable(name = "vendorId") Long vendorId
    ) {
        return new ResponseEntity<>(
                vendorService.getVendor(vendorId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedResponseDTO> getAllVendors(
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
                vendorService.getAllVendors(pageNo, count, sortBy, orderBy, search), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteVendors(
            @RequestBody List<Long> vendorIds
    ) {
        vendorService.deleteVendors(vendorIds);
        return new ResponseEntity<>(
                new ResponseData("Vendor's deleted successfully") , HttpStatus.OK);
    }

}
