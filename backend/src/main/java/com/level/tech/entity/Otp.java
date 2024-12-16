package com.level.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
@EntityListeners(AuditingEntityListener.class)
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", nullable = false)
    private Integer value;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }

}
