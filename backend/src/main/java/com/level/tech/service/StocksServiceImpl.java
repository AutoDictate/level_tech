package com.level.tech.service;

import com.level.tech.dto.PurchaseInfoDTO;
import com.level.tech.dto.StocksDTO;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.MasterProduct;
import com.level.tech.entity.Purchase;
import com.level.tech.entity.PurchaseDetails;
import com.level.tech.enums.Branch;
import com.level.tech.mapper.MasterProductMapper;
import com.level.tech.repository.MasterProductRepository;
import com.level.tech.repository.PurchaseRepository;
import com.level.tech.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StocksServiceImpl implements StocksService {

    private final MasterProductRepository masterProductRepository;

    private final MasterProductMapper masterProductMapper;

    private final PurchaseRepository purchaseRepository;

    private final SaleRepository saleRepository;

    @Override
    public PagedResponseDTO getAllStocks(
            final Branch branch,
            final Integer pageNo,
            final Integer count,
            final String sortBy,
            final String orderBy,
            final String search
    ) {
        Sort sort = orderBy.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, count, sort);

        // Fetch total purchase and sale quantities using aggregated queries
        List<Object[]> purchaseQuantities = purchaseRepository.getTotalPurchaseQuantities(branch);
        Map<Long, Integer> purchaseCount = purchaseQuantities.stream()
                .collect(Collectors.toMap(
                        obj -> (Long) obj[0],
                        obj -> ((Number) obj[1]).intValue()
                ));

        List<Object[]> salesQuantities = saleRepository.getTotalSaleQuantities(branch);
        Map<Long, Integer> salesCount = salesQuantities.stream()
                .collect(Collectors.toMap(
                        obj -> (Long) obj[0],
                        obj -> ((Number) obj[1]).intValue()
                ));

        List<StocksDTO> stocks = new ArrayList<>();

        Page<MasterProduct> pmp = masterProductRepository.findAll(pageable);

        for (MasterProduct mp : pmp.getContent()) {
            StocksDTO dto = new StocksDTO();

            // Setting MasterProductDTO
            dto.setMasterProductDTO(masterProductMapper.toDTO(mp));

            // Fetching all purchase info related to the specific product
            List<Purchase> purchases = purchaseRepository.findPurchasesByProduct(mp.getId(), branch);

            // Creating the purchaseInfoDTO list
            List<PurchaseInfoDTO> purchaseInfoList = new ArrayList<>();
            for (Purchase p : purchases) {
                PurchaseInfoDTO purchaseInfoDTO = new PurchaseInfoDTO();

                // Fill in purchase-related information
                purchaseInfoDTO.setVendor(p.getVendor().getName());
                purchaseInfoDTO.setInvoiceNo(p.getInvoiceNo());
                purchaseInfoDTO.setInvoiceDate(p.getInvoiceDate());
                purchaseInfoDTO.setPurchaseNo(p.getId());
                purchaseInfoDTO.setPurchaseDate(p.getCreatedAt().toLocalDate());

                // Correctly filter and get the quantity, rate, and amount for this specific product from purchase details
                PurchaseDetails purchaseDetail = p.getPurchaseDetails().stream()
                        .filter(pd -> pd.getMasterProduct().getId().equals(mp.getId()))
                        .findFirst()
                        .orElse(null);

                if (purchaseDetail != null) {
                    purchaseInfoDTO.setQuantity(purchaseDetail.getQuantity());
                    purchaseInfoDTO.setUnitRate(purchaseDetail.getUnitRate());
                    purchaseInfoDTO.setAmount(BigDecimal.valueOf(purchaseDetail.getAmount()));
                } else {
                    purchaseInfoDTO.setQuantity(0);
                    purchaseInfoDTO.setUnitRate(0.0);
                    purchaseInfoDTO.setAmount(BigDecimal.ZERO);
                }

                purchaseInfoList.add(purchaseInfoDTO);
            }

            // Set the purchaseInfoDTO list in the StocksDTO
            dto.setPurchaseInfoDTO(purchaseInfoList);

            // Setting stock-related information
            dto.setTotalProducts(purchaseCount.getOrDefault(mp.getId(), 0));
            dto.setSold(salesCount.getOrDefault(mp.getId(), 0));
            dto.setInStock(
                    Math.max(
                            purchaseCount.getOrDefault(mp.getId(), 0) - salesCount.getOrDefault(mp.getId(), 0),
                            0
                    )
            );

            stocks.add(dto);
        }

        return new PagedResponseDTO(
                stocks,
                pageNo + 1,
                pmp.getTotalElements(),
                pmp.getTotalPages()
        );
    }

}
