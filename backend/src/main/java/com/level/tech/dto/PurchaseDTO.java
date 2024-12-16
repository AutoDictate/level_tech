package com.level.tech.dto;

import com.level.tech.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private Long id;

    private Long vendorId;

    private String vendorName;

    private String orderNo;

    private LocalDate orderDate;

    private String gstNo;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private String modeOfDispatch;

    private String transporter;

    private String lrRrNo;

    private LocalDate lrRrDate;

    private Branch consignedTo;

    private Integer packingBox;

    private Double weight;

    private Double packing;

    private Double freight;

    private Double subtotal;

    private Double cgst;

    private Double sgst;

    private Double igst;

    private Double taxAmountGst;

    private BigDecimal totalAmount;

    private LocalDate paidDate;

    private BigDecimal paidAmount;

    private List<PurchaseDetailsDTO> purchaseDetails;

    private Boolean starred;

    private BigDecimal due;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;
}
