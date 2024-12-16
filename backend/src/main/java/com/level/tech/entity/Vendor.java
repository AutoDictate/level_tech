package com.level.tech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.level.tech.enums.Title;
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
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
@EntityListeners(AuditingEntityListener.class)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship Mapping

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "accountType_id", nullable = false)
    private AccountType accountType;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases;

    // Actual Fields

    @Column(name = "title", nullable = false)
    private Title title;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "postCode")
    private String postCode;

    @Column(name = "IFSCCode")
    private String ifscCode;

    @Column(name = "address1", columnDefinition = "TEXT")
    private String address1;

    @Column(name = "address2", columnDefinition = "TEXT")
    private String address2;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "bank_address", columnDefinition = "TEXT")
    private String bankAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "terms", columnDefinition = "TEXT")
    private String terms;

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
    private void clearAssociations() {
        this.state = null;
        this.bank = null;
    }

}
