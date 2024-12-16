package com.level.tech.mapper;

import com.level.tech.dto.PurchaseDetailsDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.request.PurchaseDetailRequest;
import com.level.tech.entity.MasterProduct;
import com.level.tech.entity.Purchase;
import com.level.tech.entity.PurchaseDetails;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.PurchaseDetailsRepository;
import com.level.tech.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseDetailsMapper {

    private final PurchaseDetailsRepository purchaseDetailsRepository;

    private final PurchaseRepository purchaseRepository;

    private final MasterProductMapper productMapper;

    @Transactional
    public PurchaseDetails toEntity(final PurchaseDetailRequest request, final Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(()-> new EntityNotFoundException("Purchase not found"));

        PurchaseDetails purchaseDetails = new PurchaseDetails();
        purchaseDetails.setTax(request.getTax());
        purchaseDetails.setAmount(request.getAmount());
        purchaseDetails.setTaxAmount(request.getTaxAmount());
        purchaseDetails.setQuantity(request.getQuantity());
        purchaseDetails.setUnitRate(request.getUnitRate());
        purchaseDetails.setIncludeTax(request.getIncludeTax());
        purchaseDetails.setMasterProduct(getMasterProduct(request));
        purchaseDetails.setPurchase(purchase);

        return purchaseDetailsRepository.save(purchaseDetails);
    }

    @Transactional
    public PurchaseDetails updatePurchaseDetails(final Long id, final PurchaseDetailRequest request) {
        PurchaseDetails purchaseDetails = purchaseDetailsRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("Purchase Details not found"));
        purchaseDetails.setTax(request.getTax());
        purchaseDetails.setAmount(request.getAmount());
        purchaseDetails.setTaxAmount(request.getTaxAmount());
        purchaseDetails.setQuantity(request.getQuantity());
        purchaseDetails.setUnitRate(request.getUnitRate());
        purchaseDetails.setIncludeTax(request.getIncludeTax());
        purchaseDetails.setMasterProduct(getMasterProduct(request));

        return purchaseDetailsRepository.save(purchaseDetails);
    }

    @Transactional
    public PurchaseDetailsDTO toDTO(final PurchaseDetails purchaseDetails) {
        MasterProduct masterProduct = purchaseDetails.getMasterProduct();

        return new PurchaseDetailsDTO(
                purchaseDetails.getId(),
                masterProduct.getId(),
                masterProduct.getCategory().getName(),
                masterProduct.getProduct().getName(),
                masterProduct.getModel().getName(),
                purchaseDetails.getQuantity(),
                purchaseDetails.getUnitRate(),
                purchaseDetails.getTax(),
                purchaseDetails.getTaxAmount(),
                purchaseDetails.getMrpRate(),
                purchaseDetails.getAmount(),
                purchaseDetails.getIncludeTax(),
                purchaseDetails.getCreatedAt(),
                purchaseDetails.getLastModifiedAt(),
                purchaseDetails.getCreatedBy(),
                purchaseDetails.getLastModifiedBy()
        );
    }

    private MasterProduct getMasterProduct(final PurchaseDetailRequest request) {
        MasterProductRequest masterProductRequest = new MasterProductRequest();
        masterProductRequest.setCategory(request.getCategoryName());
        masterProductRequest.setProduct(request.getProductName());
        masterProductRequest.setModel(request.getModelName());
        masterProductRequest.setTax(request.getTax());
        masterProductRequest.setHsnAcs(request.getHsnNo());

        return productMapper.addMasterProduct(masterProductRequest);
    }


}
