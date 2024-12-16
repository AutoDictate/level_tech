package com.level.tech.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemRequest {

    private String categoryName;

    private String productName;

    private String modelName;

    private String hsnNo;

    private Integer quantity;

    private Double mrpRate;

    private Double tax;

    private Double taxAmount;

    private Double amount;

    private String serviceRemarks;

}
