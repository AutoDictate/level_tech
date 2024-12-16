package com.level.tech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.level.tech.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document", indexes = {
        @Index(name = "idx_entity", columnList = "entityType, user_id")
})
@EntityListeners(AuditingEntityListener.class)
public class Document implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_url", nullable = false)
    private String url;

    @Column(name = "document_name")
    private String name;

    @Column(name = "entity_type")
    private EntityType entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @CreatedDate
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
