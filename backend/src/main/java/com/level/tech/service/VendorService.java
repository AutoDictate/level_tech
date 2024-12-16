package com.level.tech.service;

import com.level.tech.dto.VendorDTO;
import com.level.tech.dto.request.VendorRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.util.List;

public interface VendorService {

    VendorDTO addVendor(VendorRequest request);

    VendorDTO updateVendor(Long vendorId, VendorRequest request);

    VendorDTO getVendor(Long vendorId);

    List<VendorDTO> getVendors();

    PagedResponseDTO getAllVendors(Integer pageNo, Integer count, String sortBy, String orderBy, String search);

    void deleteVendor(Long vendorId);

    void deleteVendors(List<Long> vendorIds);

}
