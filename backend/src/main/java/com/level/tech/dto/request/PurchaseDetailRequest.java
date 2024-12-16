package com.level.tech.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailRequest {

    private String categoryName;

    private String productName;

    private String modelName;

    private String hsnNo;

    private Integer quantity;

    private Double unitRate;

    private Double tax;

    private Double taxAmount;

    private Double mrpRate;

    private Double amount;

    private Boolean includeTax;

    private Long purchaseId;

}
