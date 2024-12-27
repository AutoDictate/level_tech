package com.level.tech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_number")
@EntityListeners(AuditingEntityListener.class)
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "company_id", nullable = false)
    private Customer customer;

    public PhoneNumber(String phoneNo, Customer customer) {
        this.phoneNo = phoneNo;
        this.customer = customer;
    }

    @PreRemove
    private void clearAssociations() {
        this.customer = null;
    }
}

