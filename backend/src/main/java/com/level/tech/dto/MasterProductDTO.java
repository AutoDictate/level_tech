package com.level.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterProductDTO {

    private Long id;

    private Double tax;

    private String hsnAcs;

    private String description;

    private KeyValueDTO category;

    private KeyValueDTO product;

    private KeyValueDTO model;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
