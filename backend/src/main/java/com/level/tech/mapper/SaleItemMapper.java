package com.level.tech.mapper;

import com.level.tech.dto.SaleItemDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.request.SaleItemRequest;
import com.level.tech.entity.MasterProduct;
import com.level.tech.entity.SaleItem;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleItemMapper {

    private final SaleItemRepository saleItemRepository;

    private final MasterProductMapper productMapper;

    @Transactional
    public SaleItem toEntity(final SaleItemRequest request) {
        SaleItem saleItem = new SaleItem();
        saleItem.setTax(request.getTax());
        saleItem.setAmount(request.getAmount());
        saleItem.setTaxAmount(request.getTaxAmount());
        saleItem.setQuantity(request.getQuantity());
        saleItem.setMrpRate(request.getMrpRate());
        saleItem.setServiceRemarks(request.getServiceRemarks());
        saleItem.setMasterProduct(getMasterProduct(request));

        return saleItemRepository.save(saleItem);
    }

    @Transactional
    public SaleItem updateSaleItem(final Long id, final SaleItemRequest request) {
        SaleItem saleItem = saleItemRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Sale Item not found"));
        saleItem.setTax(request.getTax());
        saleItem.setAmount(request.getAmount());
        saleItem.setTaxAmount(request.getTaxAmount());
        saleItem.setQuantity(request.getQuantity());
        saleItem.setMrpRate(request.getMrpRate());
        saleItem.setServiceRemarks(request.getServiceRemarks());
        saleItem.setMasterProduct(getMasterProduct(request));

        return saleItemRepository.save(saleItem);
    }

    @Transactional
    public SaleItemDTO toDTO(final SaleItem saleItem) {
        MasterProduct masterProduct = saleItem.getMasterProduct();

        return new SaleItemDTO(
                saleItem.getId(),
                masterProduct.getId(),
                masterProduct.getCategory().getName(),
                masterProduct.getProduct().getName(),
                masterProduct.getModel().getName(),
                saleItem.getQuantity(),
                saleItem.getMrpRate(),
                saleItem.getTax(),
                saleItem.getTaxAmount(),
                saleItem.getAmount(),
                saleItem.getServiceRemarks(),
                saleItem.getCreatedAt(),
                saleItem.getLastModifiedAt(),
                saleItem.getCreatedBy(),
                saleItem.getLastModifiedBy()
        );
    }

    private MasterProduct getMasterProduct(final SaleItemRequest request) {
        MasterProductRequest masterProductRequest = new MasterProductRequest();
        masterProductRequest.setCategory(request.getCategoryName());
        masterProductRequest.setProduct(request.getProductName());
        masterProductRequest.setModel(request.getModelName());
        masterProductRequest.setTax(request.getTax());
        masterProductRequest.setHsnAcs(request.getHsnNo());

        return productMapper.addMasterProduct(masterProductRequest);
    }

}
