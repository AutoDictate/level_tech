package com.level.tech.dto.request;

import com.level.tech.enums.Branch;
import com.level.tech.enums.PaymentMode;
import com.level.tech.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    private Long customerId;

    private String gstNo;

    private Branch dispatchCity;

    private String transporter;

    private PaymentMode paymentMode;

    private PaymentStatus paymentStatus;

    private Boolean sameShipping;

    private String invoiceNo;

    private Integer year;

    private LocalDate billDate;

    private String deliveryNo;

    private LocalDate deliveryDate;

    private String purchaseOrderNo;

    private Boolean reverseCharge;

    private Double packingCharge;

    private Double transportCharge;

    private Double cgst;

    private Double sgst;

    private Double igst;

    private Double taxAmountGst;

    private LocalDate paidDate;

    private Double paidAmount;

    private Double subTotal;

    private Double grandTotal;

    private List<SaleItemRequest> saleItemRequest;

}
