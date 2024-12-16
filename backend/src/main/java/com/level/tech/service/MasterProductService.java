package com.level.tech.service;

import com.level.tech.dto.MasterProductDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.util.List;

public interface MasterProductService {

    MasterProductDTO addMasterProduct(MasterProductRequest request);

    MasterProductDTO updateMasterProduct(Long id, MasterProductRequest request);

    MasterProductDTO getMasterProduct(Long id);

    List<MasterProductDTO> getAllMasterProducts();

    PagedResponseDTO getAllMasterProducts(Integer pageNo, Integer count, String sortBy, String orderBy, String search);

    void deleteMasterProduct(Long id);

    void deleteMasterProducts(List<Long> ids);

}
