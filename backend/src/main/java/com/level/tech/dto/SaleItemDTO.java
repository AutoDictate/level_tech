package com.level.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDTO {

    private Long id;

    private Long masterProductId;

    private String categoryName;

    private String productName;

    private String modelName;

    private Integer quantity;

    private Double mrpRate;

    private Double tax;

    private Double taxAmount;

    private Double amount;

    private String serviceRemarks;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;

}
