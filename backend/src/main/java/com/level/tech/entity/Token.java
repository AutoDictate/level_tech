package com.level.tech.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true, columnDefinition = "TEXT")
    private String jwtToken;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "token_type")
    private String tokenType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
