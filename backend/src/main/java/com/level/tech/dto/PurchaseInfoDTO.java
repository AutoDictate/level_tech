package com.level.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInfoDTO {

    private String vendor;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private Long purchaseNo;

    private LocalDate purchaseDate;

    private Integer quantity;

    private Double unitRate;

    private BigDecimal amount;

}
