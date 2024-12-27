package com.level.tech.entity;

import com.level.tech.enums.Branch;
import com.level.tech.enums.PaymentMode;
import com.level.tech.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
@EntityListeners(AuditingEntityListener.class)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> saleItems;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "dispatch_city")
    private Branch dispatchCity;

    @Column(name = "transporter")
    private String transporter;

    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "same_shipping", nullable = false)
    private Boolean sameShipping;

    @Column(name = "invoice_no", nullable = false)
    private String invoiceNo;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @Column(name = "delivery_no")
    private String deliveryNo;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "purchase_order_no")
    private String purchaseOrderNo;

    @Column(name = "reverse_charge", nullable = false)
    private Boolean reverseCharge;

    @Column(name = "packing_charge")
    private Double packingCharge;

    @Column(name = "transport_charge")
    private double transportCharge;

    @Column(name = "cgst")
    private double cgst;

    @Column(name = "sgst")
    private double sgst;

    @Column(name = "igst")
    private double igst;

    @Column(name = "tax_amount_gst")
    private double taxAmountGst;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "paid_amount")
    private double paidAmount;

    @Column(name = "sub_total", nullable = false)
    private double subTotal;

    @Column(name = "grand_total", nullable = false)
    private double grandTotal;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @PreRemove
    public void clearAssociations() {
        this.saleItems = null;
    }

}
