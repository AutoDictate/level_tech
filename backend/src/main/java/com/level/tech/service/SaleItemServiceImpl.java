package com.level.tech.service;

import com.level.tech.dto.SaleItemDTO;
import com.level.tech.dto.request.SaleItemRequest;
import com.level.tech.entity.Sale;
import com.level.tech.entity.SaleItem;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.SaleItemMapper;
import com.level.tech.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;

    private final SaleItemMapper saleItemMapper;

    @Override
    public SaleItemDTO addSaleItem(final SaleItemRequest request, final Sale sale) {
        var savedDetails = saleItemMapper.toEntity(request, sale);
        return saleItemMapper.toDTO(savedDetails);
    }

    @Override
    public SaleItemDTO updateSaleItem(final Long id, final SaleItemRequest request) {
        var savedDetails = saleItemMapper.updateSaleItem(id, request);
        return saleItemMapper.toDTO(savedDetails);
    }

    @Override
    public SaleItemDTO getSaleItem(final Long id) {
        return saleItemRepository.findById(id)
                .map(saleItemMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Purchase Details not found"));
    }

    @Override
    public List<SaleItemDTO> getSaleItems() {
        return saleItemRepository.findAll()
                .stream()
                .map(saleItemMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteSaleItem(final Long id) {
        SaleItem saleItemDetails = saleItemRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Purchase Details not found"));

        saleItemDetails.setIsDeleted(Boolean.TRUE);
        saleItemRepository.save(saleItemDetails);
    }

    @Override
    public void deleteSaleItems(final List<Long> ids) {
        ids.forEach(this::deleteSaleItem);
    }
}
