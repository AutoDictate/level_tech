package com.level.tech.dto.request;

import com.level.tech.enums.Branch;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    @NotNull(message = "Vendor is mandatory")
    private Long vendorId;

    private String orderNo;

    private Integer packingBox;

    private String gstNo;

    private LocalDate orderDate;

    private String modeOfDispatch;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private String transporter;

    private String lrRrNo;

    private LocalDate lrRrDate;

    private Branch consignedTo;

    private Double packing;

    private Double freight;

    private Double taxTotal;

    private LocalDate paidDate;

    private BigDecimal paidAmount;

    private BigDecimal totalAmount;

    private List<PurchaseDetailRequest> purchaseDetails;
}
