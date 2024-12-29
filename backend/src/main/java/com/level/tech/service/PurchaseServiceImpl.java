package com.level.tech.service;

import com.level.tech.dto.PurchaseDTO;
import com.level.tech.dto.request.PurchaseRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Purchase;
import com.level.tech.entity.PurchaseDetails;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.PurchaseMapper;
import com.level.tech.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDetailsService purchaseDetailsService;

    private final PurchaseRepository purchaseRepository;

    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDTO addPurchase(final PurchaseRequest request) {
        var savedPurchase = purchaseMapper.toEntity(request);
        return purchaseMapper.toDTO(savedPurchase);
    }

    @Override
    public PurchaseDTO updatePurchase(final Long id, final PurchaseRequest request) {
        var savedPurchase = purchaseMapper.updateEntity(id, request);
        return purchaseMapper.toDTO(savedPurchase);
    }

    @Override
    public PurchaseDTO getPurchase(final Long id) {
        return purchaseRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .map(purchaseMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Purchase not found"));
    }

    @Override
    public List<PurchaseDTO> getPurchases() {
        return purchaseRepository.findAllByActive()
                .stream()
                .map(purchaseMapper::toDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllPurchases(
            final Integer pageNo,
            final Integer count,
            final String sortBy,
            final String orderBy,
            final String search,
            final LocalDate startDate,
            final LocalDate endDate,
            final String dateFilterBy
    ) {
        Pageable pageable = PageRequest.of(
                pageNo,
                count,
                sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Purchase> purchaseList;

        if (startDate == null || endDate == null) {
            purchaseList = (search == null || search.isBlank())
                    ? purchaseRepository.findAllByActive(pageable)
                    : purchaseRepository.findAllPurchases(search, pageable);
        } else {

            purchaseList = purchaseRepository.findAllByDateRangeAndField(
                    search,
                    startDate,
                    endDate,
                    dateFilterBy,
                    pageable
            );
        }

        List<PurchaseDTO> allPurchases = purchaseList.getContent()
                .stream()
                .map(purchaseMapper::toDTO)
                .toList();

        return purchaseMapper.purchaseResponseDTO(allPurchases, pageNo, purchaseList);
    }


    @Override
    @Transactional
    public void deletePurchase(final Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Purchase not found"));

        List<Long> purchaseDetailIds = purchase.getPurchaseDetails()
                        .stream()
                .map(PurchaseDetails::getId)
                .toList();

        purchaseDetailsService.deletePurchaseDetails(purchaseDetailIds);

        purchase.setIsDeleted(Boolean.TRUE);
        purchaseRepository.delete(purchase);
    }

    @Override
    public void deletePurchases(final List<Long> ids) {
        ids.forEach(this::deletePurchase);
    }
}
