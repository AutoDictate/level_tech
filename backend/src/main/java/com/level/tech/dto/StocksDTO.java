package com.level.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StocksDTO {

    private MasterProductDTO masterProductDTO;

    private List<PurchaseInfoDTO> purchaseInfoDTO;

    private Integer totalProducts;

    private Integer sold;

    private Integer inStock;

}
