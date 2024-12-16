package com.level.tech.entity;

import com.level.tech.enums.Branch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship Mapping

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetails> purchaseDetails;

    // Actual Fields

    @Column(name = "is_starred")
    private Boolean starred;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "mode_of_dispatch")
    private String modeOfDispatch;

    @Column(name = "transporter")
    private String transporter;

    @Column(name = "lr_rr_no")
    private String lrRrNo;

    @Column(name = "lr_rr_date")
    private LocalDate lrRrDate;

    @Column(name = "packing_box")
    private Integer packingBox;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "freight")
    private Double freight;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "cgst")
    private Double cgst;

    @Column(name = "sgst")
    private Double sgst;

    @Column(name = "igst")
    private Double igst;

    @Column(name = "tax_total")
    private Double taxTotal;

    @Column(name = "packing_total")
    private Double packingTotal;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "consigned_to")
    private Branch consignedTo;

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
    public void removeAssociations() {
        this.vendor = null;
    }

}
