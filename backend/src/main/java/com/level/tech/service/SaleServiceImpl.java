package com.level.tech.service;

import com.level.tech.dto.SaleDTO;
import com.level.tech.dto.request.SaleRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Sale;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.SaleMapper;
import com.level.tech.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    @Override
    public SaleDTO addSale(final SaleRequest request) {
        var savedSale = saleMapper.toEntity(request);
        return saleMapper.toDTO(savedSale);
    }

    @Override
    public SaleDTO updateSale(final Long saleId, final SaleRequest request) {
        var savedSale = saleMapper.updateSale(saleId, request);
        return saleMapper.toDTO(savedSale);
    }

    @Override
    public SaleDTO getSale(final Long saleId) {
        return saleRepository.findById(saleId)
                .map(saleMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Sales not found"));
    }

    @Override
    public List<SaleDTO> getSales() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllSales(
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

        Page<Sale> saleList;

        if (startDate == null || endDate == null) {
            saleList = (search == null || search.isBlank())
                    ? saleRepository.findAll(pageable)
                    : saleRepository.findAllSales(search, pageable);
        } else {
            saleList = saleRepository.findAllByDateRangeAndField(
                    search,
                    startDate,
                    endDate,
                    dateFilterBy,
                    pageable
            );
        }

        List<SaleDTO> allSales = saleList.getContent()
                .stream()
                .map(saleMapper::toDTO)
                .toList();

        return saleMapper.saleResponseDTO(allSales, pageNo, saleList);
    }

    @Override
    public void deleteSale(final Long saleId) {

    }

    @Override
    public void deleteSales(final List<Long> saleIds) {

    }
}
