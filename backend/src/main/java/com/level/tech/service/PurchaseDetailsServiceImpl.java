package com.level.tech.service;

import com.level.tech.dto.PurchaseDetailsDTO;
import com.level.tech.dto.request.PurchaseDetailRequest;
import com.level.tech.entity.PurchaseDetails;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.PurchaseDetailsMapper;
import com.level.tech.repository.PurchaseDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseDetailsServiceImpl implements PurchaseDetailsService {

    private final PurchaseDetailsRepository purchaseDetailsRepository;

    private final PurchaseDetailsMapper detailsMapper;

    @Override
    public PurchaseDetailsDTO addPurchaseDetails(final PurchaseDetailRequest request, final Long purchaseId) {
        var savedDetails = detailsMapper.toEntity(request, purchaseId);
        return detailsMapper.toDTO(savedDetails);
    }

    @Override
    public PurchaseDetailsDTO updatePurchaseDetails(final Long id, final PurchaseDetailRequest request) {
        var savedDetails = detailsMapper.updatePurchaseDetails(id, request);
        return detailsMapper.toDTO(savedDetails);
    }

    @Override
    public PurchaseDetailsDTO getPurchaseDetail(final Long id) {
        return purchaseDetailsRepository.findById(id)
                .map(detailsMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Purchase Details not found"));
    }

    @Override
    public List<PurchaseDetailsDTO> getPurchaseDetails() {
        return purchaseDetailsRepository.findAll()
                .stream()
                .map(detailsMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deletePurchaseDetail(final Long id) {
        PurchaseDetails purchaseDetails = purchaseDetailsRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Purchase Details not found"));
        purchaseDetailsRepository.delete(purchaseDetails);
    }

    @Override
    public void deletePurchaseDetails(final List<Long> ids) {
        ids.forEach(this::deletePurchaseDetail);
    }
}
