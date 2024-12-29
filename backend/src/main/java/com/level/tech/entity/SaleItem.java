package com.level.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saleItem")
@EntityListeners(AuditingEntityListener.class)
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "master_product_id", nullable = false)
    private MasterProduct masterProduct;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "mrp_rate")
    private Double mrpRate;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "service_remarks", columnDefinition = "TEXT")
    private String serviceRemarks;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

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
        this.sale = null;
        this.masterProduct = null;
    }

}

