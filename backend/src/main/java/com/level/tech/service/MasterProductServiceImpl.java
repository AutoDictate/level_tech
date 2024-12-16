package com.level.tech.service;

import com.level.tech.dto.MasterProductDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.MasterProduct;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.MasterProductMapper;
import com.level.tech.repository.MasterProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterProductServiceImpl implements MasterProductService {

    private final MasterProductRepository masterProductRepository;

    private final MasterProductMapper productMapper;

    @Override
    public MasterProductDTO addMasterProduct(final MasterProductRequest request) {
        var savedProduct = productMapper.addMasterProduct(request);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public MasterProductDTO updateMasterProduct(final Long id, final MasterProductRequest request) {
        var savedProduct = productMapper.updateMasterProduct(id, request);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public MasterProductDTO getMasterProduct(final Long id) {
        return masterProductRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Master product not found"));
    }

    @Override
    public List<MasterProductDTO> getAllMasterProducts() {
        return masterProductRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllMasterProducts(
            final Integer pageNo,
            final Integer count,
            final String sortBy,
            final String orderBy,
            final String search
    ) {
        Pageable pageable = PageRequest.of(
                pageNo,
                count,
                sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<MasterProduct> masterProductList = (search == null || search.isBlank())
                ? masterProductRepository.findAll(pageable)
                : masterProductRepository.findAllMasterProducts(search, pageable);

        List<MasterProductDTO> allMasterProducts = masterProductList.getContent()
                .stream()
                .map(productMapper::toDTO)
                .toList();

        return productMapper.masterProductResponseDTO(allMasterProducts, pageNo, masterProductList);
    }

    @Override
    public void deleteMasterProduct(final Long id) {
        MasterProduct product = masterProductRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Master product not found"));

        product.setProduct(null);
        product.setCategory(null);
        product.setModel(null);
        masterProductRepository.delete(product);
    }

    @Override
    public void deleteMasterProducts(final List<Long> ids) {
        ids.forEach(this::deleteMasterProduct);
    }
}
