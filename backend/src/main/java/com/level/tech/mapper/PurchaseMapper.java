package com.level.tech.mapper;

import com.level.tech.dto.PurchaseDTO;
import com.level.tech.dto.PurchaseDetailsDTO;
import com.level.tech.dto.request.PurchaseDetailRequest;
import com.level.tech.dto.request.PurchaseRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Purchase;
import com.level.tech.entity.PurchaseDetails;
import com.level.tech.entity.Vendor;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.PurchaseRepository;
import com.level.tech.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseMapper {

    private final PurchaseDetailsMapper purchaseDetailsMapper;

    private final PurchaseRepository purchaseRepository;

    private final VendorRepository vendorRepository;

    @Transactional
    public Purchase toEntity(final PurchaseRequest request) {
        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));

        Purchase purchase = new Purchase();
        purchase.setVendor(vendor);
        purchase.setOrderNo(request.getOrderNo());
        purchase.setPackingBox(request.getPackingBox());
        purchase.setGstNo(request.getGstNo());
        purchase.setOrderDate(request.getOrderDate());
        purchase.setModeOfDispatch(request.getModeOfDispatch());
        purchase.setInvoiceNo(request.getInvoiceNo());
        purchase.setInvoiceDate(request.getInvoiceDate());
        purchase.setTransporter(request.getTransporter());
        purchase.setLrRrNo(request.getLrRrNo());
        purchase.setLrRrDate(request.getLrRrDate());
        purchase.setConsignedTo(request.getConsignedTo());
        purchase.setPackingTotal(request.getPacking());
        purchase.setFreight(request.getFreight());
        purchase.setTaxTotal(request.getTaxTotal());
        purchase.setPaidDate(request.getPaidDate());
        purchase.setPaidAmount(request.getPaidAmount());
        purchase.setStarred(Boolean.FALSE);
        purchase.setTotalAmount(request.getTotalAmount());

        List<PurchaseDetails> purchaseDetails = new ArrayList<>();
        for (PurchaseDetailRequest req : request.getPurchaseDetails()) {
            PurchaseDetails details = purchaseDetailsMapper.toEntity(req, purchase.getId());
            purchaseDetails.add(details);
        }

        purchase.setPurchaseDetails(purchaseDetails);
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public Purchase updateEntity(final Long id, final PurchaseRequest request) {
        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));

        Purchase purchase = purchaseRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("Purchase not found"));
        purchase.setVendor(vendor);
        purchase.setOrderNo(request.getOrderNo());
        purchase.setPackingBox(request.getPackingBox());
        purchase.setGstNo(request.getGstNo());
        purchase.setOrderDate(request.getOrderDate());
        purchase.setModeOfDispatch(request.getModeOfDispatch());
        purchase.setInvoiceNo(request.getInvoiceNo());
        purchase.setInvoiceDate(request.getInvoiceDate());
        purchase.setTransporter(request.getTransporter());
        purchase.setLrRrNo(request.getLrRrNo());
        purchase.setLrRrDate(request.getLrRrDate());
        purchase.setConsignedTo(request.getConsignedTo());
        purchase.setPackingTotal(request.getPacking());
        purchase.setFreight(request.getFreight());
        purchase.setTaxTotal(request.getTaxTotal());
        purchase.setPaidDate(request.getPaidDate());
        purchase.setPaidAmount(request.getPaidAmount());
        purchase.setStarred(Boolean.FALSE);

        List<PurchaseDetails> purchaseDetails = new ArrayList<>();
        for (PurchaseDetailRequest req : request.getPurchaseDetails()) {
            var details = purchaseDetailsMapper.toEntity(req, purchase.getId());
            purchaseDetails.add(details);
        }

        purchase.setPurchaseDetails(purchaseDetails);
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public PurchaseDTO toDTO(Purchase purchase) {
        List<PurchaseDetailsDTO> purchaseDetailsDTOs = purchase.getPurchaseDetails()
                .stream()
                .map(purchaseDetailsMapper::toDTO)
                .toList();

        BigDecimal due = purchase.getTotalAmount().subtract(purchase.getPaidAmount());

        return new PurchaseDTO(
                purchase.getId(),
                purchase.getVendor().getId(),
                purchase.getVendor().getName(),
                purchase.getOrderNo(),
                purchase.getOrderDate(),
                purchase.getGstNo(),
                purchase.getInvoiceNo(),
                purchase.getInvoiceDate(),
                purchase.getModeOfDispatch(),
                purchase.getTransporter(),
                purchase.getLrRrNo(),
                purchase.getLrRrDate(),
                purchase.getConsignedTo(),
                purchase.getPackingBox(),
                purchase.getWeight(),
                purchase.getPackingTotal(),
                purchase.getFreight(),
                purchase.getSubtotal(),
                purchase.getCgst(),
                purchase.getSgst(),
                purchase.getIgst(),
                purchase.getTaxTotal(),
                purchase.getTotalAmount(),
                purchase.getPaidDate(),
                purchase.getPaidAmount(),
                purchaseDetailsDTOs,
                purchase.getStarred(),
                due,
                purchase.getCreatedAt(),
                purchase.getLastModifiedAt(),
                purchase.getCreatedBy(),
                purchase.getLastModifiedBy()
        );
    }

    public PagedResponseDTO purchaseResponseDTO(
            final List<PurchaseDTO> allPurchases,
            final Integer pageNo,
            final Page<Purchase> purchaseList
    ) {
        return new PagedResponseDTO(
                allPurchases,
                pageNo + 1,
                purchaseList.getTotalElements(),
                purchaseList.getTotalPages()
        );
    }

}
